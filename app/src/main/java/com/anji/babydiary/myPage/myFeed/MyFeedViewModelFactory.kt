package com.anji.babydiary.myPage.myFeed

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import java.lang.IllegalArgumentException

class MyFeedViewModelFactory(val database:MainFeedDAO, val profileDatabas:ProfileDao, val application: Application) :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MyFeedViewModel::class.java)) {
            return MyFeedViewModel(database, profileDatabas, application) as T
        }
        throw IllegalArgumentException()

    }

}