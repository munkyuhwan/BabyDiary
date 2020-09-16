package com.anji.babydiary.common

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import kotlinx.coroutines.*

class ProfileDBChecker(val context:Context) {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    val database = ProfileDatabase.getInstance(context).database

    val nameArray = arrayOf(
        "",
        "승율아가",
        "찬호",
        "쥬쥬",
        "오쑥이",
        "선우",
        "승현아기",
        "말랑이",
        "재재"
    )

    val intro= arrayOf(
        "",
        "승유리를 소개해요",
        "똘망똘망한 먹보",
        "포토그래퍼의 보물",
        "우리집 사랑둥이",
        "착할선 번우",
        "이슬부부의 뮤즈",
        "작고 소중한 우리의 천사",
        "세상으로 나오는 날을 기다리는 중"
    )

    val imgArray = arrayOf(
        "",
        "mem_1",
        "mem_2",
        "mem_3",
        "mem_4",
        "mem_5",
        "mem_6",
        "mem_7",
        "mem_8"
    )


    /*
    companion object {
        @Volatile private var instance: ProfileDBChecker? = null

        @JvmStatic fun getInstance(): ProfileDBChecker =
            instance ?: synchronized(this) {
                instance ?: ProfileDBChecker().also {
                    instance = it
                }
            }

    }

     */

    private var _profileResult = MutableLiveData<List<Profiles>>()
    var profileResult:LiveData<List<Profiles>>
        get() = _profileResult

        set(value) {}

    fun checkProfile(context:Context) {

        uiScope.launch {
            selectProfile(database)
        }

    }

    suspend private fun selectProfile(database:ProfileDao) {
        withContext(Dispatchers.IO) {
            _profileResult.postValue( database.selectAll().value )
        }
    }


    fun addProfile() {
        doInsert(1)
        doInsert(2)
        doInsert(3)
        doInsert(4)
        doInsert(5)
        doInsert(6)
        doInsert(7)
        doInsert(8)
    }
    fun doInsert(i:Int) {
        //for (i in 1..10) {
        var profile = Profiles()
        profile.idx = i.toLong()
        profile.name = nameArray[i]
        profile.id = i
        profile.pass = "${i}"
        profile.introduce = "${intro[i]}"
        profile.imgTmp = imgArray[i]
        uiScope.launch {
            insert(profile)
        }
        //}
    }

    private suspend fun insert(profile:Profiles) {
        withContext(Dispatchers.IO){
            Log.e("dataInsert", "member insert")
            database.insert(profile)
        }
    }

}

// 사용하기
//val singleton = ProfileDBChecker.getInstance()