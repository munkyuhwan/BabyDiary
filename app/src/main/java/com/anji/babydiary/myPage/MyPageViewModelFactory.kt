package com.anji.babydiary.myPage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyPageViewModelFactory (val application: Application):ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            return MyPageViewModel(application) as T
        }
        throw IllegalArgumentException()
    }

}