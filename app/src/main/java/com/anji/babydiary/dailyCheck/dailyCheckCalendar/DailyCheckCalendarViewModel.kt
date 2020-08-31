package com.anji.babydiary.dailyCheck.dailyCheckCalendar

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import kotlinx.coroutines.*

class DailyCheckCalendarViewModel(val database:DailyCheckDao, application: Application) : AndroidViewModel(application) {

    var selectedYear = MutableLiveData<String>()
    var selectedMonth = MutableLiveData<String>()
    var selectedDate = MutableLiveData<String>()
    var selectedDay = MutableLiveData<String>()
    var dataToday = MutableLiveData<List<DailyCheck>>()

    var isVisible = MutableLiveData<Int>()

    val days = arrayOf(
        "",
        "SUN",
        "MON",
        "TUE",
        "WED",
        "THU",
        "FRI",
        "SAT"
    )
    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        isVisible.value = View.GONE

    }

    fun onDaySelect(day:Int) {
        selectedDay.value = days[day]
        isVisible.value = View.VISIBLE
        uiScope.launch {
            selecteByDate()
        }
    }


    suspend fun selecteByDate() {
        withContext(Dispatchers.IO) {
            dataToday.postValue(database.selectByDate(selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), CommonCode.USER_IDX) )
        }
    }

}