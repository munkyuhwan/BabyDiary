package com.anji.babydiary.gnb.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NavViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NavViewModel::class.java)) {
            return NavViewModel(application) as T
        }
        throw IllegalArgumentException()
    }

}