package com.anji.babydiary.dailyCheck.listAdapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class DailyCheckListAdapterViewModelFactory ():ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DailyCheckListAdapterViewModel::class.java)) {
            return DailyCheckListAdapterViewModel() as T
        }
        throw IllegalArgumentException()

    }

}