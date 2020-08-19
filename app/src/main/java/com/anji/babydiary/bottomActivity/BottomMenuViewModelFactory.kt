package com.anji.babydiary.bottomActivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BottomMenuViewModelFactory (val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BottomMenuViewModel::class.java)) {
            return BottomMenuViewModel(application) as T
        }
        throw IllegalArgumentException()
    }

}