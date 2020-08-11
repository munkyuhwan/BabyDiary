package com.anji.babydiary.mainFeed

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainFeedViewModelFactory(private val application: Application):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFeedViewModel::class.java)) {
            return MainFeedViewModel(application) as T
        }
        throw IllegalArgumentException()

    }


}