package com.anji.babydiary.dailyCheck.listAdapter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import java.lang.IllegalArgumentException

class DailyCheckListAdapterViewModelFactory (val database: DailyCheckDao, val application: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DailyCheckListAdapterViewModel::class.java)) {
            return DailyCheckListAdapterViewModel(database, application) as T
        }
        throw IllegalArgumentException()

    }

}