package com.anji.babydiary.mainFeed.feedDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.likes.Likes
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.Dispatcher

class FeedDetailViewModel(val idx:Long, val database:MainFeedDAO, val likeDatabase:LikesDao, val profileDatabase: ProfileDao) : ViewModel() {

    val select = MutableLiveData<MainFeed>()
    val writtenDate = MutableLiveData<String>()

    val likeCount = likeDatabase.selectAllByFeedIdx(idx)
    val likeTotal = MutableLiveData<Int>()
    private val viewModelJob = Job()
    val uiScope = CoroutineScope( Dispatchers.Main + viewModelJob)
    val writerProfile = MutableLiveData<Profiles>()

    init {
        uiScope.launch {
            getInitialInf()
        }
    }

    private suspend fun getInitialInf() {
        withContext(Dispatchers.IO ) {
            select.postValue( database.selectSingle(idx) )
            writerInfo()
        }
    }

    fun onLikeButtonClicked(likeCnt:CharSequence) {
        var like:Likes = Likes()
        like.feed_idx = idx
        like.user_idx = MyShare.prefs.getLong("idx", 0L)
        like.date = System.currentTimeMillis()

        uiScope.launch {
            likeInsert(like)
        }

        uiScope.launch {
            updateLike(likeCnt)
        }

    }

    private fun writerInfo() {
        uiScope.launch {
            getWriterInfo()
        }
    }

    private suspend fun getWriterInfo() {
        withContext(Dispatchers.IO) {
            writerProfile.postValue( profileDatabase.selectProfile(select.value!!.userIdx) )
        }
    }

    private suspend fun likeInsert(like:Likes) {
        withContext(Dispatchers.IO) {
            likeDatabase.insert(like)
        }
    }


    suspend fun updateLike(likeCnt:CharSequence) {
        var like = likeCnt.toString().replace("공감 ","").replace("개","").toLong()+1
        withContext(Dispatchers.IO) {
            database.updateLike(like, idx)
        }

    }

}