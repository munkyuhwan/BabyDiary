package com.anji.babydiary.myPage.chatting.chattingList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.chatting.ChattingDao

class ChattingListViewModelFactory (val database: ChattingDao, val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChattingListViewModel::class.java)) {
            return ChattingListViewModel(database, application) as T
        }
        throw IllegalArgumentException()
    }

}



