package com.anji.babydiary.myPage.myFollower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.follow.Follow
import com.anji.babydiary.database.follow.FollowDao
import com.anji.babydiary.database.follow.FollowDatabase
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import kotlinx.coroutines.*
import org.json.JSONObject

class FollowerViewModel(val database: FollowDao, val userIdx:Long, application: Application) : AndroidViewModel(application) {


    val selectAllFollowee = MutableLiveData<List<Follow>>()
    val selectAllFollower = MutableLiveData<List<Follow>>()
    val selectAll = MutableLiveData<List<Follow>>()

    val profileDatabase = ProfileDatabase.getInstance(application).database
    var profileResult = MutableLiveData<List<Profiles>>()

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        uiScope.launch {
            selectAllFollowee()
            selectAllFollower()
            selectAll()
        }
    }

    suspend fun selectAllFollowee() {
        withContext(Dispatchers.IO) {
            selectAllFollowee.postValue(database.selectFollowee(userIdx))
        }
    }

    suspend fun selectAllFollower() {
        withContext(Dispatchers.IO) {
            selectAllFollower.postValue( database.selectFollower(userIdx) )
        }
    }

    suspend fun selectAll() {
        withContext(Dispatchers.IO) {
            selectAll.postValue( database.selectAllList() )
        }
    }




}