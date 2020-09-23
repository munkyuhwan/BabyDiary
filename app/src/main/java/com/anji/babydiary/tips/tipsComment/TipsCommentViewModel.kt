package com.anji.babydiary.tips.tipsComment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anji.babydiary.common.MyShare.MyShare
import com.anji.babydiary.database.profile.ProfileDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.Tips
import com.anji.babydiary.database.tip.tipsComment.TipsComment
import com.anji.babydiary.database.tip.tipsComment.TipsCommentDao
import kotlinx.coroutines.*

class TipsCommentViewModel(val database:TipsCommentDao, val tipIdx:Long,  application: Application) : AndroidViewModel(application) {

    val dataList = database.selectByTipOnlyIdx(tipIdx)
    val profileDatabase = ProfileDatabase.getInstance(application).database

    val dataProfile = profileDatabase.selectProfile(MyShare.prefs.getLong("idx",0))


    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {


    }



    fun completeWrite(text:CharSequence) {

        var tipComment = TipsComment()
        tipComment.tipIdx = tipIdx
        tipComment.userIdx = MyShare.prefs.getLong("idx",0)
        tipComment.commentText = text.toString()

        uiScope.launch {
            insertTipComment(tipComment)
        }

    }

    suspend fun insertTipComment(tipComment:TipsComment) {
        withContext(Dispatchers.IO) {
            database.insert(tipComment)
        }

    }

    fun updateTipComment(idx:Long, newText:String) {
        uiScope.launch{
            queryUpdateTipComment(idx, newText)
        }
    }
    suspend fun queryUpdateTipComment(commentIdx:Long, newText:String) {
        withContext(Dispatchers.IO) {
            database.updateTipComment(commentIdx, newText)
        }
    }

    fun deleteTipCOmment(idx:Long) {
        uiScope.launch{
            queryDeleteTipComment(idx)
        }
    }
    suspend fun queryDeleteTipComment(idx:Long) {
        withContext(Dispatchers.IO) {
            database.deleteTipComment(idx)
        }
    }

}

class TipEditClicked(val editClickListener:(resultId:Long, newText:CharSequence?)->Unit) {
    fun onEditCLick(result: TipsComment, newText:CharSequence?) = editClickListener(result.idx, newText)
}

class TipDeleteClicked(val deleteClickListener:(resultId:Long)->Unit) {
    fun onDeleteCLick(result: TipsComment) = deleteClickListener(result.idx)
}

