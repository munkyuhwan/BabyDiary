package com.anji.babydiary.mainFeed.feedDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO

class FeedDetailViewModel(val idx:Long, val database:MainFeedDAO) : ViewModel() {

    val select = database.selectSingle(idx)
    val writtenDate = MutableLiveData<String>()

    init {

    }


}