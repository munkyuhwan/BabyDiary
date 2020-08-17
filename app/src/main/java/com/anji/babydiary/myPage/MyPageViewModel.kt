package com.anji.babydiary.myPage

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MyPageViewModel (application: Application):AndroidViewModel(application) {

    var isMain = MutableLiveData<Int>();
    var isSub = MutableLiveData<Int>();

    init {
        isMain.value = View.GONE
        isSub.value = View.GONE

    }

}