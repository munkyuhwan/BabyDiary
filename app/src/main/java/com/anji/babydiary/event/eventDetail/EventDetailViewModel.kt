package com.anji.babydiary.event.eventDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.database.event.EventDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class EventDetailViewModel(val database: EventDao, val eventIdx:Long=0L) : ViewModel() {

    var job = Job()
    var uiScope = CoroutineScope(Dispatchers.Main + job)


    lateinit var eventDetail: LiveData<Event>


    init {
        getData()
    }

    fun getData() {
        eventDetail=database.selectIdx(eventIdx)
    }

}