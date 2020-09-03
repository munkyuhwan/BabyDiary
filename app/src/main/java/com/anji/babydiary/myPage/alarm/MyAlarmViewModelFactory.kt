package com.anji.babydiary.myPage.alarm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.alarm.AlarmDao
import java.lang.IllegalArgumentException

class MyAlarmViewModelFactory(val database: AlarmDao, val application: Application) :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAlarmViewModel::class.java)) {
            return MyAlarmViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}