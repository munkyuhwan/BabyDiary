package com.anji.babydiary.dailyCheck

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import java.lang.IllegalArgumentException

class DailyCheckViewModelFactory(val database:DailyCheckDao, val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyCheckViewModel::class.java)) {
            return DailyCheckViewModel(
                database,
                application
            ) as T
        }
        throw IllegalArgumentException()

    }

}