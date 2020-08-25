package com.anji.babydiary.myPage.myFeed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MyFeedViewModel(val database:MainFeedDAO, application: Application) : AndroidViewModel(application) {


    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val selectAll = database.selectAll()
    init {

    }


}

class MyFeedClickListener(val clickListener:(feedId:Long)->Unit) {
    fun onMyFeedClicked(mainFeed:MainFeed) = clickListener(mainFeed.idx)
}

