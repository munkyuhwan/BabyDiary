package com.anji.babydiary.dailyCheck.dailyCheckWrite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.dailyCheck.DailyCheckViewModel
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import java.lang.IllegalArgumentException

class DailyCheckWriteViewModelFactory(val database: DailyCheckDao,val idx:Long, val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyCheckWriteViewModel::class.java)) {
            return DailyCheckWriteViewModel(database,idx, application) as T
        }
        throw  IllegalArgumentException()
    }

}