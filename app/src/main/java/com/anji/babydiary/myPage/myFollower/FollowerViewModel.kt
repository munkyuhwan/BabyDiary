package com.anji.babydiary.myPage.myFollower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.follow.FollowDao
import com.anji.babydiary.database.follow.FollowDatabase

class FollowerViewModel(val database: FollowDao, application: Application) : AndroidViewModel(application) {

    init {

    }

}