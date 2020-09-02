package com.anji.babydiary.myPage.chatting.chattingRoom

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anji.babydiary.database.chatting.ChattingDao
import java.lang.IllegalArgumentException

class ChattingRoomViewModelFactory(val database:ChattingDao,val userIdxOne:Long, val application:Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChattingRoomViewModel::class.java) ) {
            return ChattingRoomViewModel(database, userIdxOne, application) as T
        }

        throw IllegalArgumentException()

    }

}