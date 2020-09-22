package com.anji.babydiary.myPage.myFollower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.follow.Follow
import com.anji.babydiary.database.follow.FollowDao
import com.anji.babydiary.database.follow.FollowDatabase

class FollowerViewModel(val database: FollowDao, val userIdx:Long, application: Application) : AndroidViewModel(application) {


    val selectAllFollowee = MutableLiveData<List<Follow>>()
    val selectAllFollower = MutableLiveData<List<Follow>>()
    val selectAll = MutableLiveData<List<Follow>>()

    init {
        selectAllFollowee.value = database.selectFollowee(userIdx)
        selectAllFollower.value = database.selectFollower(userIdx)
        selectAll.value = database.selectAllList()
    }

}