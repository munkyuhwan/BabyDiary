package com.anji.babydiary.myPage.chatting.ChattingList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.chatting.ChattingDao
import java.lang.IllegalArgumentException

class ChattingListViewModelFactory (val database:ChattingDao, val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChattingListViewModel::class.java)) {
            return ChattingListViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}




