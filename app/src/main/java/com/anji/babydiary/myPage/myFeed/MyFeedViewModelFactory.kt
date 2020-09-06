package com.anji.babydiary.myPage.myFeed

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.follow.FollowDao
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import java.lang.IllegalArgumentException

class MyFeedViewModelFactory(val idx:Long, val database:MainFeedDAO, val profileDatabas:ProfileDao, val followDatabase:FollowDao, val application: Application) :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MyFeedViewModel::class.java)) {
            return MyFeedViewModel(idx, database, profileDatabas, followDatabase, application) as T
        }
        throw IllegalArgumentException()

    }

}