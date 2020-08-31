package com.anji.babydiary.mainFeed.feedDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.mainFeed.MainFeedDatabase
import com.anji.babydiary.database.profile.ProfileDao
import java.lang.IllegalArgumentException


class FeedDetailViewModelFactory(val idx:Long, val database:MainFeedDAO, val likeDatabase:LikesDao, val profileDatabase: ProfileDao):ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedDetailViewModel::class.java)) {
            return FeedDetailViewModel(idx, database, likeDatabase, profileDatabase) as T
        }
        throw IllegalArgumentException()
    }

}