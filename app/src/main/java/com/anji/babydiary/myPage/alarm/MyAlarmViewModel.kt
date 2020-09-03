package com.anji.babydiary.myPage.alarm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.alarm.AlarmDao

class MyAlarmViewModel(val database:AlarmDao, application: Application) : AndroidViewModel(application) {


    val selectAll  = database.selectAll()

    init {

    }

}