package com.anji.babydiary.tips.tipsComment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.tip.tipsComment.TipsCommentDao
import java.lang.IllegalArgumentException

class TipsCommentViewModelFactory (val database: TipsCommentDao, val tipIdx:Long, val application: Application):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TipsCommentViewModel::class.java)) {
            return TipsCommentViewModel(database, tipIdx, application) as T
        }
        throw IllegalArgumentException()
    }

}