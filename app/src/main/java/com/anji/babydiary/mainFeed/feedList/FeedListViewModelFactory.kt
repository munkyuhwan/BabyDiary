package com.anji.babydiary.mainFeed.feedList

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.bookmark.BookMark
import com.anji.babydiary.database.bookmark.BookMarkDao
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.likes.LikesDatabase
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import java.lang.IllegalArgumentException

class FeedListViewModelFactory(
    private val mainFeedDAO: MainFeedDAO,
    private val profiles: ProfileDao,
    private val likesDao: LikesDao,
    private val bookMark: BookMarkDao,
    private val activity: Activity,
    private val application: Application
):ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            return FeedListViewModel(mainFeedDAO, profiles,likesDao, bookMark, activity ,application) as T
        }

        throw IllegalArgumentException()
    }

}