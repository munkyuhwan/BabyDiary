package com.anji.babydiary.myPage.chatting.ChattingList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.chatting.ChattingDao

class ChattingListViewModel(val database:ChattingDao, application: Application) : AndroidViewModel(application) {



    init {

    }


}

class OnChattingListClick(val chattingClickListener:(resultId:Long)->Unit) {
    fun onClick(chatting:Chatting) = chattingClickListener(chatting.idx)
}

