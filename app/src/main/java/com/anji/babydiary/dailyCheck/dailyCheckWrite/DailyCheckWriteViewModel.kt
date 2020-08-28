package com.anji.babydiary.dailyCheck.dailyCheckWrite

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.CountUpTimer
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class DailyCheckWriteViewModel(val database: DailyCheckDao,val idx:Long, application: Application) : AndroidViewModel(application) {

    var selectedYear = MutableLiveData<String>()
    var selectedMonth = MutableLiveData<String>()
    var selectedDate = MutableLiveData<String>()
    var selectedDay = MutableLiveData<String>()

    var calendarVisibility = MutableLiveData<Int>()
    var detailVisibility = MutableLiveData<Int>()

    var inputVisibility = MutableLiveData<Int>()

    val isCountDown = MutableLiveData<Int>()
    val isMemo = MutableLiveData<Int>()

    var selectedIndex = MutableLiveData<Int>()

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    var dataToday = MutableLiveData<List<DailyCheck>>()
    var selectedData = MutableLiveData<DailyCheck>()
    val marginTop = MutableLiveData<Float>()


    var leftCounting = MutableLiveData<Int>()
    var rightCounting = MutableLiveData<Int>()
    var isLeftCountingStarted = MutableLiveData<Boolean>()
    var isRightCountingStarted = MutableLiveData<Boolean>()

    val initText = MutableLiveData<String>()

    init {
        calendarVisibility.value = View.VISIBLE
        detailVisibility.value = View.GONE
        inputVisibility.value = View.GONE
        isMemo.value = View.GONE
        isCountDown.value = View.GONE
        marginTop.value = 0F

        leftCounting.value = 0
        rightCounting.value = 0
        isLeftCountingStarted.value = true
        isRightCountingStarted.value = true

        uiScope.launch {
            selecteByDate()
        }

    }

    suspend fun selecteByDate() {
        withContext(Dispatchers.IO) {
            dataToday.postValue(database.selectByDate(selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), idx) )
        }
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
    }

    suspend fun delete() {
        withContext(Dispatchers.IO) {
            database.delete()

        }
    }

    fun initializeFields() {
        initText.value = ""
        leftCounting.value = 0
        rightCounting.value = 0
        isLeftCountingStarted.value = true
        isRightCountingStarted.value = true
    }

    fun saveData(memo:CharSequence) {


        //uiScope.launch {
        //    delete()
        //}

        initializeFields()


        //입력되었는지 확인
        uiScope.launch {
            dataCheck(memo.toString())
        }


    }

    var globalMemo = ""
    suspend fun dataCheck(memo:String) {
        globalMemo = memo
        withContext(Dispatchers.IO) {
            selectedData.postValue(database.selectByCategory(
                selectedIndex.value!!.toInt(),
                selectedYear.value!!.toInt(),
                selectedMonth.value!!.toInt(),
                selectedDate.value!!.toInt(),
                idx
                )
            )

            insertUpdateData()

        }
    }
    fun insertUpdateData() {

        val currentHour = Utils.getDate(System.currentTimeMillis(), "HH")
        val currentMinute = Utils.getDate(System.currentTimeMillis(), "mm")

        if (selectedData.value == null) {
            //입력값이 없으면 insert

            var data = DailyCheck()
            data.category = selectedIndex.value!!
            if (DailyCheckListObj.itemInput[selectedIndex.value!!]) {
                //메모
                data.valueOne = globalMemo
            }else {
                //카운트 업
                data.valueOne = leftCounting.value!!.toString()
                data.valueTwo = rightCounting.value!!.toString()
            }
            data.year = selectedYear.value!!.toInt()
            data.month = selectedMonth.value!!.toInt()
            data.date = selectedDate.value!!.toInt()
            data.hour = currentHour!!.toInt()
            data.minute = currentMinute!!.toInt()
            data.user_idx = CommonCode.USER_IDX

            saveMemo(data)

        }else {
            //update

            uiScope.launch {
                if (DailyCheckListObj.itemInput[selectedIndex.value!!]) {
                    //메모
                    update(globalMemo,"",currentHour.toString(), currentMinute.toString())
                }else {
                    //카운트 업
                    update(globalMemo,globalMemo,currentHour.toString(), currentMinute.toString())
                }
            }
        }

    }

    suspend fun update(valueOne:String, valueTwo:String,currentHour:String, currentMin:String) {
        withContext(Dispatchers.IO) {
            database.update(valueOne, valueTwo, selectedIndex.value!!, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), idx)
            uiScope.launch {
                selecteByDate()
            }
        }
    }

    fun saveMemo(dailyCheck:DailyCheck) {
        uiScope.launch {
            insertData(dailyCheck)
        }
    }

    suspend fun insertData(dailyCheck: DailyCheck) {
        Log.e("savedata","=====wuery!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")
        withContext(Dispatchers.IO) {
            Log.e("savedata","=====with context!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")
            database.insert(dailyCheck)
            uiScope.launch {
                selecteByDate()
            }
        }
    }

    fun startCounting(isLeft:Boolean) {
        if (isLeft && leftCounting.value!! > 0 ) {
            Log.e("is left couting:" ,"${isLeftCountingStarted.value!!}")
            if (isLeftCountingStarted.value!!) {
                stopCounting(isLeft)
            }else {
                isLeftCountingStarted.value = true
                uiScope.launch {
                    count(isLeft)
                }
            }
        }else {
            uiScope.launch {
                count(isLeft)
            }
        }

        if(!isLeft && rightCounting.value!! > 0){
            if (isRightCountingStarted.value!!) {
                stopCounting(isLeft)
            }else {
                isRightCountingStarted.value = true
                uiScope.launch {
                    count(isLeft)
                }
            }
        }else {
            uiScope.launch {
                count(isLeft)
            }
        }
    }

    suspend fun count(isLeft:Boolean) {
        withContext(Dispatchers.IO) {
            if (isLeft) {
                while (isLeftCountingStarted.value!!) {
                    Thread.sleep(1000)
                    leftCounting.postValue(leftCounting.value!! + 1)
                }
            }else {
                while (isRightCountingStarted.value!!) {
                    Thread.sleep(1000)
                    rightCounting.postValue(rightCounting.value!! + 1)
                }
            }
        }

    }

    fun stopCounting(isLeft:Boolean) {
        if (isLeft) {
            isLeftCountingStarted.value = false
        }else {
            isRightCountingStarted.value = false
        }

    }


}