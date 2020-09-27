package com.anji.babydiary.dailyCheck.listAdapter

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class DailyCheckListAdapterViewModel :ViewModel() {

    var isEditing = MutableLiveData<Boolean>()
    val isCountDown = MutableLiveData<Int>()


    init {
        isEditing.value = true


    }

    fun startEditing() {
        if(isEditing.value == true) {
            isEditing.value = false
        }else {
            isEditing.value = true
        }
        Log.e("isediting","${isEditing.value}")
    }

}