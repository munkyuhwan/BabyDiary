package com.anji.babydiary.dailyCheck.dailyCheckCalendar

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import java.lang.IllegalArgumentException

class DailyCheckCalendarViewModelFactory(val database:DailyCheckDao, val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(DailyCheckCalendarViewModel::class.java)) {
            return DailyCheckCalendarViewModel(database, application) as T
        }
        throw IllegalArgumentException()

    }

}