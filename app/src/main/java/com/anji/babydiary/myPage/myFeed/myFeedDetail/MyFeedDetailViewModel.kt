package com.anji.babydiary.mainFeed.feedDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.likes.Likes
import com.anji.babydiary.database.likes.LikesDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.Dispatcher

class MyFeedDetailViewModel(val idx:Long, val database:MainFeedDAO, val likeDatabase:LikesDao) : ViewModel() {

    val select = database.selectSingle(idx)
    val writtenDate = MutableLiveData<String>()

    val likeCount = likeDatabase.selectAllByFeedIdx(idx)
    val likeTotal = MutableLiveData<Int>()
    private val viewModelJob = Job()
    val uiScope = CoroutineScope( Dispatchers.Main + viewModelJob)

    init {
        Log.e("like","like count: ${likeCount}")

    }

    fun onLikeButtonClicked() {
        var like:Likes = Likes()

        like.feed_idx = idx
        like.user_idx = CommonCode.USER_IDX
        like.date = System.currentTimeMillis()


        uiScope.launch {
            likeInsert(like)
        }

    }

    private suspend fun likeInsert(like:Likes) {
        withContext(Dispatchers.IO) {
            likeDatabase.insert(like)
        }
    }


}