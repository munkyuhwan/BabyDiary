package com.anji.babydiary.comment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.comments.Comments
import com.anji.babydiary.database.comments.CommentsDao
import com.anji.babydiary.database.mainFeed.MainFeed
import kotlinx.coroutines.*

class CommentViewModel(application: Application, val database:CommentsDao, val idx:Long) : AndroidViewModel(application) {

    val totalList = database.selectAllByFeedIdx(idx)

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {

    }


    fun completeComment(text:CharSequence) {

        var comment:Comments = Comments()

        comment.userIdx = 0
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



}





