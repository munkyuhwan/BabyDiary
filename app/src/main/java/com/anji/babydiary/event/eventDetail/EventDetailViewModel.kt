package com.anji.babydiary.event.eventDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.database.event.EventDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class EventDetailViewModel(val database: EventDao, application: Application) : AndroidViewModel(application) {

    var job = Job()
    var uiScope = CoroutineScope(Dispatchers.Main + job)

    init {

    }

}