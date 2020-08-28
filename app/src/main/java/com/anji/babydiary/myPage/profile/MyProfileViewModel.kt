package com.anji.babydiary.myPage.profile

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.profile.ProfileDao
import kotlinx.coroutines.*

class MyProfileViewModel(val idx:Long, val database: ProfileDao, application: Application) : AndroidViewModel(application) {
    var selectedImage = MutableLiveData<Uri>()
    var data = MutableLiveData<Profiles>()

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        uiScope.launch {
            getInitialData()
        }
    }

    private suspend fun getInitialData() {
        withContext(Dispatchers.IO) {

           data.postValue( database.selectProfile(idx) )
        }
    }

    fun onImageSelected(data: Intent?) {
        Log.e("data","${data!!.data}")
        selectedImage.value = data?.data!!
    }

    fun onSubmitClick(name:CharSequence, pass:CharSequence, introduce:CharSequence) {

        data.let {
            it?.let {
                var profile = Profiles()

                profile.name = name.toString()
                profile.pass = pass.toString()
                profile.introduce = introduce.toString()

                selectedImage.value?.let {
                    profile.img = it.toString()
                }

                if (it.value == null) {
                    insertData(profile)
                }else {
                    updateData(profile)
                }

            }
        }

    }

    fun insertData(profile:Profiles) {
        uiScope.launch {
            insert(profile)
        }
    }

    fun updateData(profile:Profiles) {

        uiScope.launch {
            update(profile)
        }

    }

    private suspend fun update(profile:Profiles) {
        withContext(Dispatchers.IO){
            database.updateQuery(profile.img, profile.name, profile.pass, profile.introduce, idx)
        }
    }

    private suspend fun insert(profile:Profiles) {
        withContext(Dispatchers.IO){
            database.insert(profile)
        }
    }
}