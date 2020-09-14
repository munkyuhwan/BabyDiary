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

    var isDone = MutableLiveData<Boolean>()

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        uiScope.launch {
            getInitialData()
        }
        isDone.value = false
    }

    private suspend fun getInitialData() {
        withContext(Dispatchers.IO) {
            val tmpData = database.selectProfileData(idx)
            data.postValue( tmpData )
            Log.e("data","data:${data}")
            if (tmpData != null) {
                selectedImage.postValue(Uri.parse(tmpData.img))
            }
        }
    }

    fun onImageSelected(data: Intent?) {
        Log.e("data","${data!!.data}")
        selectedImage.value = data?.data!!
    }

    fun onSubmitClick(name:CharSequence, pass:CharSequence, introduce:CharSequence) {

        //data.let {
         //   it?.let {
                var profile = Profiles()

                profile.name = name.toString()
                profile.pass = pass.toString()
                profile.introduce = introduce.toString()

                profile.img = selectedImage.value.toString()

                if (data.value == null) {
                    insertData(profile)
                }else {
                    updateData(profile)
                }

        //    }
        //}

    }

    fun insertData(profile:Profiles) {
        uiScope.launch {
            insert(profile)
            isDone.value = true
        }
    }

    fun updateData(profile:Profiles) {

        uiScope.launch {
            update(profile)
            isDone.value = true
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