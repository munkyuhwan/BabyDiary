package com.anji.babydiary.comment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.comments.CommentsDao
import com.anji.babydiary.database.profile.ProfileDao
import com.anji.babydiary.database.profile.Profiles
import java.lang.IllegalArgumentException

class CommentViewModelFactory (val application: Application, val database:CommentsDao, val profileDatabase:ProfileDao, val idx:Long):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java) ) {
            return CommentViewModel(application, database, profileDatabase, idx) as T
        }
        throw IllegalArgumentException()

    }

}