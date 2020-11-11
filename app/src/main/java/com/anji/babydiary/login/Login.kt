package com.anji.babydiary.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anji.babydiary.R
import com.anji.babydiary.common.BaseActivity
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.common.Utils
import com.anji.babydiary.dailyCheck.DailyCheckListObj
import com.anji.babydiary.database.dailyCheck.DailyCheck
import com.anji.babydiary.database.dailyCheck.DailyCheckDao
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.databinding.ActivityLoginBinding
import com.anji.babydiary.introduction.IntroductionActivity
import com.anji.babydiary.mainFeed.MainFeedActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : BaseActivity() {
    lateinit var dailyCheckDatabase: DailyCheckDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        dailyCheckDatabase = DailyCheckDatabase.getInstance(applicationContext).database

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        val application = getApplication()
        val database = ProfileDatabase.getInstance(applicationContext).database
        val viewModelFactory = LoginViewModelFactory(database, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)


        binding.viewModel = viewModel

        viewModel.idx.observe(this, Observer {
            it?.let {
                if (it != 0L) {
                    MyShare.prefs.setLong("idx", it)

                    // 데이터 저장

                    Log.e("user","========================================================")
                    Log.e("user","${it}")
                    Log.e("user","${MyShare.prefs.getLong("idx", 0L)}")
                    Log.e("user","========================================================")


                    startInsert(0, "8.1kg", 2020, 12,1, 10,30)
                    startInsert(0, "8.1kg", 2020, 12,2, 14,20)
                    startInsert(0, "8.2kg", 2020, 12,3, 12,10)
                    startInsert(0, "8.3kg", 2020, 12,4, 16,0)
                    startInsert(0, "8.3kg", 2020, 12,5, 17,23)
                    startInsert(0, "8.3kg", 2020, 12,6, 18,11)
                    startInsert(0, "8.4kg", 2020, 12,7, 12,23)
                    startInsert(0, "8.5kg", 2020, 12,8, 9,25)
                    startInsert(0, "8.5kg", 2020, 12,9, 11,26)
                    startInsert(0, "8.6kg", 2020, 12,10, 15,27)
                    startInsert(0, "8.8kg", 2020, 12,11, 19,14)
                    startInsert(0, "9.0kg", 2020, 12,12, 20,36)
                    startInsert(0, "9.1kg", 2020, 12,13, 18,47)

                    startInsert(1, "69.1cm", 2020, 12,1, 10,45)
                    startInsert(1, "69.4cm", 2020, 12,2, 14,25)
                    startInsert(1, "69.6cm", 2020, 12,3, 12,14)
                    startInsert(1, "70.0cm", 2020, 12,4, 16,15)
                    startInsert(1, "70.1cm", 2020, 12,5, 17,35)
                    startInsert(1, "70.1cm", 2020, 12,6, 18,46)
                    startInsert(1, "70.1cm", 2020, 12,7, 12,34)
                    startInsert(1, "70.2cm", 2020, 12,8, 9,56)
                    startInsert(1, "70.3cm", 2020, 12,9, 11,56)
                    startInsert(1, "70.4cm", 2020, 12,10, 15,23)
                    startInsert(1, "70.5cm", 2020, 12,11, 19,35)
                    startInsert(1, "70.6cm", 2020, 12,12, 20,12)
                    startInsert(1, "70.6cm", 2020, 12,13, 18,56)


                    startInsert(2, "42.1cm", 2020, 12,1, 10,45)
                    startInsert(2, "42.4cm", 2020, 12,2, 14,56)
                    startInsert(2, "42.6cm", 2020, 12,3, 12,35)
                    startInsert(2, "42.0cm", 2020, 12,4, 16,57)
                    startInsert(2, "43.1cm", 2020, 12,5, 17,23)
                    startInsert(2, "43.1cm", 2020, 12,6, 18,53)
                    startInsert(2, "43.1cm", 2020, 12,7, 12,34)
                    startInsert(2, "43.2cm", 2020, 12,8, 9,44)
                    startInsert(2, "43.3cm", 2020, 12,9, 11,56)
                    startInsert(2, "43.4cm", 2020, 12,10, 15,56)
                    startInsert(2, "43.5cm", 2020, 12,11, 19,23)
                    startInsert(2, "43.6cm", 2020, 12,12, 20,56)
                    startInsert(2, "43.6cm", 2020, 12,13, 18,26)

                    startInsert(3, "", 2020, 12,1, 10,45 ,"900","920")
                    startInsert(3, "", 2020, 12,2, 14,56 ,"940","920")
                    startInsert(3, "", 2020, 12,3, 12,35 ,"950","920")
                    startInsert(3, "", 2020, 12,4, 16,57 ,"880","920")
                    startInsert(3, "", 2020, 12,5, 17,23 ,"900","920")
                    startInsert(3, "", 2020, 12,6, 18,53 ,"910","920")
                    startInsert(3, "", 2020, 12,7, 12,34 ,"900","920")
                    startInsert(3, "", 2020, 12,8, 9,44  ,"920","920")
                    startInsert(3, "", 2020, 12,9, 11,56 ,"930","920")
                    startInsert(3, "", 2020, 12,10, 15,56,"920","920")
                    startInsert(3, "", 2020, 12,11, 19,23,"950","920")
                    startInsert(3, "", 2020, 12,12, 20,56,"920","920")
                    startInsert(3, "", 2020, 12,13, 18,26,"910","920")

                    startInsert(10, "깨끗이~", 2020, 12,2, 11,56 )
                    startInsert(10, "샴푸 바꿈", 2020, 12,3, 15,56)
                    startInsert(10, "새샴푸 괜찮음", 2020, 12,6, 19,23)
                    startInsert(10, "보디샴푸 바꿈", 2020, 12,11, 20,56)
                    startInsert(10, "냄새 좋음", 2020, 12,12, 20,56)
                    startInsert(10, "물온도 낮았음", 2020, 12,8, 18,26)
                    startInsert(10, "적절한 온도", 2020, 12,13, 18,26)


                    val intent = Intent(this, IntroductionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
    fun startInsert(index:Int, memo:String, year:Int, month:Int, date:Int, hour:Int, minute:Int, leftCounting:String="",rightCounting:String=""){
        uiScope.launch {
            dataCheck(index, memo, year, month, date, hour, minute, leftCounting,rightCounting)
        }
    }


    suspend fun dataCheck(index:Int, memo:String, year:Int, month:Int, date:Int, hour:Int, minute:Int, leftCounting:String="",rightCounting:String="") {
        withContext(Dispatchers.IO) {

            var selectedData = dailyCheckDatabase.selectByCategory(
                index,
                year,
                month,
                date,
                MyShare.prefs.getLong("idx", 0L)
            )
            if (selectedData == null) {
                //입력값이 없으면 insert

                var data = DailyCheck()
                data.category = index
                if (DailyCheckListObj.itemInput[index]) {
                    //메모
                    data.valueOne = memo
                }else {
                    //카운트 업
                    data.valueOne = leftCounting
                    data.valueTwo = rightCounting
                }
                data.year = year
                data.month = month
                data.date = date
                data.hour = hour
                data.minute = minute
                data.user_idx = MyShare.prefs.getLong("idx", 0L)

                saveMemo(data)

            }else {
                //update

                uiScope.launch {
                    if (DailyCheckListObj.itemInput[index]) {
                        //메모
                        update(index, memo, year, month, date, hour, minute)
                    }else {
                        //카운트 업
                        update(index, memo, year, month, date, hour, minute, leftCounting,rightCounting)
                    }
                }
            }

        }
    }
    suspend fun update(index:Int, memo:String, year:Int, month:Int, date:Int, hour:Int, minute:Int, leftCounting:String="",rightCounting:String="") {
        withContext(Dispatchers.IO) {
            if(leftCounting == "") {
                dailyCheckDatabase.update(leftCounting, rightCounting, index, year, month, date, MyShare.prefs.getLong("idx", 0L))
            }else {
                dailyCheckDatabase.update(memo, "", index, year, month, date, MyShare.prefs.getLong("idx", 0L))
            }
        }
    }

    fun insertDailyCheck(index:Int, memo:String, year:Int, month:Int, date:Int, hour:Int, minute:Int,leftCounting:String="",rightCounting:String="") {
        uiScope.launch {
            dataCheck(index, memo, year, month, date, hour, minute ,leftCounting,rightCounting)
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
            dailyCheckDatabase.insert(dailyCheck)

        }
    }

}