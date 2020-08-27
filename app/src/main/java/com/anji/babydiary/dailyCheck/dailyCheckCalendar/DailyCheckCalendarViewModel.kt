package com.anji.babydiary.dailyCheck.dailyCheckCalendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DailyCheckCalendarViewModel(val database:DailyCheckDao, application: Application) : AndroidViewModel(application) {

    var selectedYear = MutableLiveData<String>()
    var selectedMonth = MutableLiveData<String>()
    var selectedDate = MutableLiveData<String>()
    var selectedDay = MutableLiveData<String>()
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


    }

    fun onDaySelect(day:Int) {

        selectedDay.value = days[day]

    }

}