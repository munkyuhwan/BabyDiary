package com.anji.babydiary.gnb.myPage

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyPageNavViewModel(application: Application) : AndroidViewModel(application) {

    var isMain = MutableLiveData<Int>()

    init {

    }



}