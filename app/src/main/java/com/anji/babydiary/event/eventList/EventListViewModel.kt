package com.anji.babydiary.event.eventList

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.database.event.Event
import com.anji.babydiary.database.event.EventDao
import kotlinx.coroutines.*

class EventListViewModel(val database:EventDao, application: Application) : AndroidViewModel(application) {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    var selectedImage = MutableLiveData<Uri>()

    val selectAll = database.selectAll()
    init {

    }


    fun insertData(image:String, title:String, text:String ) {
        Log.e("insert","======================================================")
        Log.e("insert","insert")
        Log.e("insert","======================================================")

        uiScope.launch {
            var event = Event()
            event.title = "${title}"
            event.text = "${text}"
            event.imgDir = "${image}"
            insert(event)
        }
    }

    suspend fun insert(event:Event) {
        withContext(Dispatchers.IO) {
            database.insert(event)
        }
    }

    fun deleteData() {
        uiScope.launch {
            delete()
        }
    }
    suspend fun delete() {
        withContext(Dispatchers.IO) {
            database.deleteAll()
        }
    }

    fun onImageSelected(data: Intent?) {
        selectedImage.value = data?.data!!
        //insertData()
    }

}


class EventListClickListener(val clickListener:(eventIdx:Long)->Unit) {
    fun onEventClick(event: Event) = clickListener(event.idx)
}

