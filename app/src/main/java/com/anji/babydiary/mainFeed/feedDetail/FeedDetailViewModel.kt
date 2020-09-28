package com.anji.babydiary.mainFeed.feedDetail

import android.util.Log
import androidx.lifecycle.LiveData
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

    var select = database.selectSingle(idx)


    val likeCount = likeDatabase.selectAllByFeedIdx(idx)
    private val viewModelJob = Job()
    val uiScope = CoroutineScope( Dispatchers.Main + viewModelJob)
    var writerProfile = MutableLiveData<Profiles>()

    var writerIdx:Long = 0L

    init {

    }

    fun startSelectWriteProfile(userIdx:Long) {
        uiScope.launch {
            selectWriterProfile(userIdx)
        }
    }


    suspend fun selectWriterProfile(userIdx:Long) {
        withContext(Dispatchers.IO) {
            writerProfile.postValue( profileDatabase.selectProfileData(userIdx) )
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