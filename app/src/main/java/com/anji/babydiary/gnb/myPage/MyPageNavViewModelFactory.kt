package com.anji.babydiary.gnb.myPage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyPageNavViewModelFactory(val application: Application) :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyPageNavViewModel::class.java)) {
            return MyPageNavViewModel(application) as T
        }
        throw IllegalArgumentException()
    }

}