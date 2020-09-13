package com.anji.babydiary.myPage.myFollower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.follow.FollowDao
import com.anji.babydiary.database.follow.FollowDatabase

class FollowerViewModel(val database: FollowDao, val userIdx:Long, application: Application) : AndroidViewModel(application) {


    val selectAllFollowee = database.selectFollowee(userIdx)
    val selectAllFollower = database.selectFollower(userIdx)


    init {

    }

}