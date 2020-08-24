package com.anji.babydiary.common.dailyCheck

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.Resource

class DailyCheckViewModel (val appCompatActivity: AppCompatActivity):ViewModel() {

    var selectedMonth = MutableLiveData<String>()
    var selectedDate = MutableLiveData<String>()
    var selectedDay = MutableLiveData<String>()

    var calendarVisibility = MutableLiveData<Int>()
    var detailVisibility = MutableLiveData<Int>()

    var inputVisibility = MutableLiveData<Int>()
    var resultDataMarginTop = MutableLiveData<Int>()

    var selectedIndex = MutableLiveData<Int>()

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




    init {
        calendarVisibility.value = View.VISIBLE
        detailVisibility.value = View.GONE
        inputVisibility.value = View.GONE
        resultDataMarginTop.value = 0
    }

    fun onDaySelect(day:Int) {

        selectedDay.value = days[day]

    }

    fun directToDetail() {
        calendarVisibility.value = View.GONE
        detailVisibility.value = View.VISIBLE
    }

    fun directToCalendar() {
        calendarVisibility.value = View.VISIBLE
        detailVisibility.value = View.GONE
    }

    fun onItemClicked(item:Int) {
        inputVisibility.value = View.VISIBLE
        selectedIndex.value = item
        //selectedBackground.value = appCompatActivity.resources.getIdentifier("circle_orange","drawable", appCompatActivity.getPackageName())
        //selectedSrc.value = appCompatActivity.resources.getIdentifier("weight_icon","drawable", appCompatActivity.getPackageName())

    }

}