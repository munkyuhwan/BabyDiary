package com.anji.babydiary.common.dailyCheck

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import java.lang.IllegalArgumentException

class DailyCheckViewModelFactory(val database:DailyCheckDao,val appCompatActivity: AppCompatActivity) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyCheckViewModel::class.java)) {
            return DailyCheckViewModel(database, appCompatActivity) as T
        }
        throw IllegalArgumentException()

    }

}