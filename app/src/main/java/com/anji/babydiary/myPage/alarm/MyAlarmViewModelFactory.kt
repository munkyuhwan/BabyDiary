package com.anji.babydiary.myPage.alarm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyAlarmViewModelFactory(val application: Application) :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAlarmViewModel::class.java)) {
            return MyAlarmViewModel(application) as T
        }
        throw IllegalArgumentException()
    }

}