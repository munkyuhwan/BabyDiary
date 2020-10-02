package com.anji.babydiary.comment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.comments.Comments
import com.anji.babydiary.database.comments.CommentsDao
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.mainFeed.MainFeedDAO
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import kotlinx.coroutines.*

class CommentViewModel(application: Application, val database:CommentsDao, val profileDatabase: ProfileDao, val feedDatabase:MainFeedDAO, val idx:Long) : AndroidViewModel(application) {

    val totalList = database.selectAllByFeedIdx(idx)
    //val totalList = database.selectAllByFeedIdxWithProfile(idx)

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    val profile = profileDatabase.selectProfile(MyShare.prefs.getLong("idx", 0L))
    val feed = feedDatabase.selectSingle(idx)
    var feedWriterProfile = MutableLiveData<Profiles>()
    init {
    }



    fun completeComment(text:CharSequence) {

        var comment:Comments = Comments()

        comment.userIdx = MyShare.prefs.getLong("idx", 0L)
        comment.depth = 0
        comment.date = System.currentTimeMillis()
        comment.commentIdx = 0
        comment.feedIdx = idx
        comment.commentText = text.toString()

        uiScope.launch {
            insertComment(comment)
        }

    }

    private suspend fun insertComment(comments: Comments) {

        withContext(Dispatchers.IO) {
            database.insert(comments)
        }

    }

    fun getProfile(userIdx:Long) {
        uiScope.launch {
            queryProfile(userIdx)
        }
    }
    suspend fun queryProfile(userIdx: Long) {
        withContext(Dispatchers.IO) {
            feedWriterProfile.postValue(profileDatabase.selectProfileData(userIdx))
        }
    }

    fun deleteComment(idx:Long) {
        uiScope.launch {
            queryDelete(idx)
        }
    }

    suspend fun queryDelete(idx:Long) {
        withContext(Dispatchers.IO) {
            database.deleteByIdx(idx)
        }
    }

}


class CommentIdClick(val clickListener:(name:String)->Unit) {
    fun onIdClick(name:String) = clickListener(name)
}

class CommentDeleteClick(val clickListener:(idx:Long)->Unit) {
    fun onDeleteClick(comm:Comments) = clickListener(comm.idx)
}





