package com.anji.babydiary.dailyCheck.dailyCheckWrite

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class DailyCheckWriteViewModel(val database: DailyCheckDao, application: Application) : AndroidViewModel(application) {

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

    //var getToaday = database.selectAll()

    var dataToday = database.selectAll()

    var selectedData = MutableLiveData<DailyCheck>()



    val marginTop = MutableLiveData<Float>()
    init {
        calendarVisibility.value = View.VISIBLE
        detailVisibility.value = View.GONE
        inputVisibility.value = View.GONE
        isMemo.value = View.GONE
        isCountDown.value = View.GONE
        marginTop.value = 0F
        uiScope.launch {
            //selecteByDate()
        }
    }

    suspend fun selecteByDate() {
        withContext(Dispatchers.IO) {
           // dataToday.postValue(database.selectByDate(selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt()) )

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

    fun saveData(memo:CharSequence) {


        uiScope.launch {
            delete()
        }




        //입력되었는지 확인
        uiScope.launch {
           // dataCheck(memo.toString())
        }


        /*
        if (dataToday.value!!.size <=0 ) {
            //insert
            Log.e("savedata","=====insert!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")

            var dailyCheck = DailyCheck()

            //dailyCheck.weight = memo.toString()
            when(selectedIndex.value) {
                0 -> {/*"몸무게",*/
                    dailyCheck.weight = memo.toString()
                }
                1 -> {/*"키",*/
                    dailyCheck.height = memo.toString()
                }
                2 -> {/*"머리둘레",*/
                    dailyCheck.head = memo.toString()
                }
                3 -> {/*"수유",*/
                }
                4 -> {/*"유축수유",*/
                }
                5 -> {/*"유축",*/
                }
                6 -> {/*"분유",*/
                    dailyCheck.powder = memo.toString()
                }
                7 -> {/*"이유식",*/
                    dailyCheck.food = memo.toString()
                }
                8 -> {/*"간식",*/
                    dailyCheck.sub_food = memo.toString()
                }
                9 -> {/*"배변",*/
                    dailyCheck.diaper = memo.toString()
                }
                10 -> {/*"수면",*/
                }
                11 -> {/*"목욕",*/
                    dailyCheck.bath = memo.toString()
                }
                12 -> {/*"놀이",*/
                    dailyCheck.play = memo.toString()
                }
                13 -> {/*"병원",*/
                    dailyCheck.hospital = memo.toString()
                }
                14 -> {/*"체온",*/
                    dailyCheck.temperature = memo.toString()
                }
                15 -> {/*"약",*/
                    dailyCheck.pill = memo.toString()
                }
                16 -> {/*"주사/예방접종",*/
                    dailyCheck.injection = memo.toString()
                }
                17 -> {/*"기타"*/
                    dailyCheck.etc = memo.toString()
                }
            }

            dailyCheck.year = selectedYear.value!!.toInt()
            dailyCheck.month = selectedMonth.value!!.toInt()
            dailyCheck.date = selectedDate.value!!.toInt()
            dailyCheck.hour = currentHour!!.toInt()
            dailyCheck.minute = currentMinute!!.toInt()

            saveMemo(dailyCheck)
        }else {
            //update
            Log.e("savedata","=====update!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!===============================================")
            setData(selectedIndex.value!!, memo.toString(), currentHour!!.toInt(), currentMinute!!.toInt())

        }

         */


    }

    var globalMemo = ""
    suspend fun dataCheck(memo:String) {
        globalMemo = memo
        withContext(Dispatchers.IO) {
            selectedData = database.selectByCategory(
                selectedIndex.value!!.toInt(),
                selectedYear.value!!.toInt(),
                selectedMonth.value!!.toInt(),
                selectedDate.value!!.toInt()
            )

            Log.e(
                "selectedData",
                "=================================================================="
            )
            Log.e("selectedData", "${selectedData}")
            Log.e(
                "selectedData",
                "=================================================================="
            )
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
                data.valueOne = "0"
                data.valueTwo = "0"
            }
            data.year = selectedYear.value!!.toInt()
            data.month = selectedMonth.value!!.toInt()
            data.date = selectedDate.value!!.toInt()
            data.hour = currentHour!!.toInt()
            data.minute = currentMinute!!.toInt()

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
            database.update(valueOne, valueTwo, selectedIndex.value!!, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt())
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
        }
    }

/*

    fun setData(index:Int, memo:String, currentHour:Int, currentMin:Int) {
        uiScope.launch {
            when(index) {
                0 -> {/*"몸무게",*/
                    updateWeight(memo, currentHour, currentMin)
                }
                1 -> {/*"키",*/
                    updateHeight(memo, currentHour, currentMin)
                }
                2 -> {/*"머리둘레",*/
                    updateHead(memo, currentHour, currentMin)
                }
                3 -> {/*"수유",*/
                }
                4 -> {/*"유축수유",*/
                }
                5 -> {/*"유축",*/
                }
                6 -> {/*"분유",*/
                    updatePowder(memo, currentHour, currentMin)
                }
                7 -> {/*"이유식",*/
                    updateFood(memo, currentHour, currentMin)
                }
                8 -> {/*"간식",*/
                    updatesubFood(memo, currentHour, currentMin)
                }
                9 -> {/*"배변",*/
                    updateDiaper(memo, currentHour, currentMin)

                }
                10 -> {/*"수면",*/
                }
                11 -> {/*"목욕",*/
                    updateBath(memo, currentHour, currentMin)
                }
                12 -> {/*"놀이",*/
                    updatePlay(memo, currentHour, currentMin)
                }
                13 -> {/*"병원",*/
                    updateHospital(memo, currentHour, currentMin)
                }
                14 -> {/*"체온",*/
                    updateTemperature(memo, currentHour, currentMin)
                }
                15 -> {/*"약",*/
                    updatePill(memo, currentHour, currentMin)
                }
                16 -> {/*"주사/예방접종",*/
                    updateInjection(memo, currentHour, currentMin)
                }
                17 -> {/*"기타"*/
                    updateEtc(memo, currentHour, currentMin)
                }
            }
        }


    }


    suspend fun updateWeight(weight:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateWeight(weight, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin )
        }
    }

    suspend fun updateHeight(weight:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateHeight(weight, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }

    suspend fun updateHead(head:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateHead(head, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }

    suspend fun updatePowder(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updatePowder(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }

    suspend fun updateFood(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateFood(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updatesubFood(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updatesubFood(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateDiaper(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateDiaper(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateSleep(str:Int, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateSleep(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateBath(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateBath(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updatePlay(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updatePlay(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateHospital(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateHospital(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateTemperature(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateTemperature(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updatePill(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updatePill(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateInjection(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateInjection(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateEtc(str:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateEtc(str, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }


    suspend fun updateBreastFeed(left:String,right:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateBreastFeed(left, right, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateBreastPumpFeed(left:String,right:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateBreastPumpFeed(left, right, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
    suspend fun updateBreastSaveFeed(left:String,right:String, currentHour: Int, currentMin: Int) {
        withContext(Dispatchers.IO) {
            database.updateBreastSaveFeed(left, right, selectedYear.value!!.toInt(), selectedMonth.value!!.toInt(), selectedDate.value!!.toInt(), currentHour, currentMin)
        }
    }
*/






}