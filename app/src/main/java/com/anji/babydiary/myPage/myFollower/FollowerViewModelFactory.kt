package com.anji.babydiary.myPage.myFollower

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.follow.FollowDao
import java.lang.IllegalArgumentException

class FollowerViewModelFactory (val database:FollowDao, val userIdx:Long, val application: Application):ViewModelProvider.Factory {


    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowerViewModel::class.java)) {
            return FollowerViewModel(database, userIdx, application) as T
        }
        throw IllegalArgumentException()
    }


}
