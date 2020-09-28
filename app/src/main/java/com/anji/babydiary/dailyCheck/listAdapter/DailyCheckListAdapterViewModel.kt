package com.anji.babydiary.dailyCheck.listAdapter

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import kotlinx.coroutines.*

class DailyCheckListAdapterViewModel(val database: DailyCheckDao,  application: Application) :AndroidViewModel(application) {

    var isEditing = MutableLiveData<Boolean>()
    val isCountDown = MutableLiveData<Int>()

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        isEditing.value = true


    }

    fun completeEdit(idx:Long, textOne:String, textTwo:String) {
        uiScope.launch {
            updateData(idx, textOne, textTwo)
        }
    }
    suspend fun updateData(idx:Long, textOne:String, textTwo:String) {
        withContext(Dispatchers.IO) {
            database.updateByIdx(textOne, textTwo, idx)
        }
    }

}