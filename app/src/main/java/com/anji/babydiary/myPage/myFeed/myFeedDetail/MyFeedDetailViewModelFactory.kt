package com.anji.babydiary.mainFeed.feedDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import java.lang.IllegalArgumentException


class MyFeedDetailViewModelFactory(val idx:Long, val database:MainFeedDAO, val likeDatabase:LikesDao):ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedDetailViewModel::class.java)) {
            return FeedDetailViewModel(idx, database, likeDatabase) as T
        }
        throw IllegalArgumentException()
    }

}