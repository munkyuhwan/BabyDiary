package com.anji.babydiary.mainFeed.feedList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import java.lang.IllegalArgumentException

class FeedListViewModelFactory(
    private val mainFeedDAO: MainFeedDAO,
    private val profiles: ProfileDao,
    private val application: Application
):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            return FeedListViewModel(mainFeedDAO, profiles, application) as T
        }

        throw IllegalArgumentException()
    }

}