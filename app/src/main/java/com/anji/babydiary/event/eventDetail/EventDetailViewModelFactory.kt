package com.anji.babydiary.event.eventDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.database.event.EventDao
import com.anji.babydiary.event.EventViewModel
import java.lang.IllegalArgumentException

class EventDetailViewModelFactory (val database: EventDao, val eventIdx:Long ):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EventDetailViewModel::class.java)) {
            return EventDetailViewModel(database, eventIdx) as T
        }

        throw IllegalArgumentException()

    }

}