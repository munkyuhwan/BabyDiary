package com.anji.babydiary.tips.writeTip

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.shopping.TipsDao
import java.lang.IllegalArgumentException

class WriteTipViewModelFactory(val database:TipsDao, val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteTipViewModel::class.java)) {
            return WriteTipViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}