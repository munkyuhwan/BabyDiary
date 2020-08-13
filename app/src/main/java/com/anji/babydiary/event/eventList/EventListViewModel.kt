package com.anji.babydiary.event.eventList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.event.EventDao

class EventListViewModel(val database:EventDao, application: Application) : AndroidViewModel(application) {


    init {

    }

}