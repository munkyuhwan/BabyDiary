package com.anji.babydiary.myPage.myFeed

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import java.lang.IllegalArgumentException

class MyFeedViewModelFactory(val database:MainFeedDAO, val application: Application) :ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MyFeedViewModel::class.java)) {
            return MyFeedViewModel(database, application) as T
        }
        throw IllegalArgumentException()

    }

}