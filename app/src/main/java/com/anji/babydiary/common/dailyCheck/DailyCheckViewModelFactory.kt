package com.anji.babydiary.common.dailyCheck

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class DailyCheckViewModelFactory(val appCompatActivity: AppCompatActivity) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyCheckViewModel::class.java)) {
            return DailyCheckViewModel(appCompatActivity) as T
        }
        throw IllegalArgumentException()

    }

}