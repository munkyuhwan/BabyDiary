package com.anji.babydiary.event.eventList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EventListViewModelFactory(val application: Application):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EventListViewModel::class.java)) {
            return EventListViewModel(application) as T
        }
        throw IllegalArgumentException()
    }

}