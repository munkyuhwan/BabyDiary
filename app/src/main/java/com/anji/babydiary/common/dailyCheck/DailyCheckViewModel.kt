package com.anji.babydiary.common.dailyCheck

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.*
import java.sql.Date

class DailyCheckViewModel (val database:DailyCheckDao ,val appCompatActivity: AppCompatActivity):ViewModel() {

    var selectedYear = MutableLiveData<String>()
    var selectedMonth = MutableLiveData<String>()
    var selectedDate = MutableLiveData<String>()
    var selectedDay = MutableLiveData<String>()

    var calendarVisibility = MutableLiveData<Int>()
    var detailVisibility = MutableLiveData<Int>()

    var inputVisibility = MutableLiveData<Int>()
    var resultDataMarginTop = MutableLiveData<Int>()

    var selectedIndex = MutableLiveData<Int>()

    val isCountDown = MutableLiveData<Int>()
    val isMemo = MutableLiveData<Int>()

    val leftCount = MutableLiveData<Int>()
    val rightCount = MutableLiveData<Int>()

    var dataToday = MutableLiveData<DailyCheck>()

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
        calendarVisibility.value = View.VISIBLE
        detailVisibility.value = View.GONE
        inputVisibility.value = View.GONE
        resultDataMarginTop.value = 0
        isMemo.value = View.GONE
        isCountDown.value = View.GONE


    }

    suspend fun selectToadyData() {
        withContext(Dispatchers.IO) {
            dataToday.postValue( database.selectByDate(selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt()) )
            Log.e("todya","${dataToday.value}")
        }
    }

    fun onDaySelect(day:Int) {

        selectedDay.value = days[day]

    }

    fun directToDetail() {
        calendarVisibility.value = View.GONE
        detailVisibility.value = View.VISIBLE

        uiScope.launch {
            selectToadyData()
        }
    }

    fun directToCalendar() {
        calendarVisibility.value = View.VISIBLE
        detailVisibility.value = View.GONE

        calendarVisibility.value = View.VISIBLE
        detailVisibility.value = View.GONE
        inputVisibility.value = View.GONE
        resultDataMarginTop.value = 0
        isMemo.value = View.GONE
        isCountDown.value = View.GONE
    }

    fun onItemClicked(item:Int) {
        inputVisibility.value = View.VISIBLE

        if (DailyCheckListObj.itemInput[item]) {
            isMemo.value = View.VISIBLE
            isCountDown.value = View.GONE
        }else {
            isMemo.value = View.GONE
            isCountDown.value = View.VISIBLE
        }

        selectedIndex.value = item
        //selectedBackground.value = appCompatActivity.resources.getIdentifier("circle_orange","drawable", appCompatActivity.getPackageName())
        //selectedSrc.value = appCompatActivity.resources.getIdentifier("weight_icon","drawable", appCompatActivity.getPackageName())

    }

    fun saveData(memo:CharSequence) {

        val currentHour = Utils.getDate(System.currentTimeMillis(), "HH")
        val currentMinute = Utils.getDate(System.currentTimeMillis(), "mm")

        Log.e("savedata","====================================================")
        Log.e("savedata","memo: ${memo}")
        Log.e("savedata","memo: ${selectedIndex.value}")
        Log.e("savedata","memo: ${selectedYear.value}")
        Log.e("savedata","memo: ${selectedMonth.value}")
        Log.e("savedata","memo: ${selectedDate.value}")
        Log.e("savedata","memo: ${Utils.getDate(System.currentTimeMillis(), "YYYY-mm-dd HH:mm")}")
        Log.e("savedata","memo: ${currentHour}. ${currentMinute}")
        Log.e("savedata","today: ${dataToday.value}")
        Log.e("savedata","====================================================")

        if (dataToday.value == null) {
            //insert
            Log.e("savedata","=====insert!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")

            var dailyCheck = DailyCheck()

            dailyCheck.weight = memo.toString()
            dailyCheck.year = selectedYear.value!!.toInt()
            dailyCheck.month = selectedMonth.value!!.toInt()
            dailyCheck.date = selectedDate.value!!.toInt()
            dailyCheck.hour = currentHour!!.toInt()
            dailyCheck.minute = currentMinute!!.toInt()

            saveMemo(dailyCheck, "weight")
        }else {
            //update


        }


    }


    fun saveMemo(dailyCheck:DailyCheck, category:String) {
        uiScope.launch {
            insertData(dailyCheck)
        }

    }

    suspend fun insertData(dailyCheck: DailyCheck) {
        withContext(Dispatchers.IO) {
            database.insert(dailyCheck)
        }
    }


    fun startCountRight() {

    }
    fun pauseCountRight() {

    }
    fun startCountLeft() {

    }
    fun pauseCountLeft() {

    }
    fun resetCount(){

    }

}
