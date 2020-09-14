package com.anji.babydiary.myPage

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import kotlinx.coroutines.*

class MyPageViewModel (val database: ProfileDao, val idx:Long, application: Application):AndroidViewModel(application) {

    var isMain = MutableLiveData<Int>();
    var isSub = MutableLiveData<Int>();

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    val myProfile = database.selectProfile(idx)

    var selectAll = database.selectAll()

    init {
        isMain.value = View.GONE
        isSub.value = View.GONE

    }


}