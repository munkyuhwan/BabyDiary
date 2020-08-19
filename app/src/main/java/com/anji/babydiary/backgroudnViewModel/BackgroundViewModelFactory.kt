package com.anji.babydiary.backgroudnViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BackgroundViewModelFactory :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BackgroundViewModel::class.java)) {
            return BackgroundViewModel() as T
        }
        throw IllegalArgumentException()
    }

}