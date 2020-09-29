package com.anji.babydiary.introduction

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class IntroductionViewModelFactory (val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(IntroductionViewModel::class.java)) {
            return IntroductionViewModel(application) as T
        }
        throw IllegalArgumentException()

    }

}