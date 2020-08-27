package com.anji.babydiary.dailyCheck

import android.app.Application
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.Utils
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import kotlinx.coroutines.*

class DailyCheckViewModel (val database:DailyCheckDao , application: Application):AndroidViewModel(application) {

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
    var allData = database.selectAll()
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

        Log.e("alldata", "${allData.value}");

    }

    suspend fun selectToadyData() {
        withContext(Dispatchers.IO) {
            Log.e("date","date: ${selectedYear.value!!.toInt()}. ${selectedMonth.value!!.toInt()}.  ${selectedDate.value!!.toInt()}")
            //dataToday.postValue( database.selectByDate(selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt()) )

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
        Log.e("savedata","memo: ${Utils.getDate(System.currentTimeMillis(), "YYYY-MM-dd HH:mm")}")
        Log.e("savedata","memo: ${currentHour}. ${currentMinute}")
        Log.e("savedata","today: ${dataToday.value}")
        Log.e("savedata","====================================================")

        if (dataToday.value == null) {
            //insert
            Log.e("savedata","=====insert!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")

            var dailyCheck = DailyCheck()

            //dailyCheck.weight = memo.toString()
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
        Log.e("savedata","=====wuery!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")
        withContext(Dispatchers.IO) {
            Log.e("savedata","=====with context!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")
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
