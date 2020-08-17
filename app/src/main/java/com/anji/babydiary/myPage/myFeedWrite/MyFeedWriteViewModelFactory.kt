package com.anji.babydiary.myPage.myFeedWrite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.myPage.myFeed.MyFeedViewModel
import java.lang.IllegalArgumentException

class MyFeedWriteViewModelFactory (val database:MainFeedDAO, val application: Application): ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyFeedWriteViewModel::class.java)) {
            return MyFeedWriteViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}