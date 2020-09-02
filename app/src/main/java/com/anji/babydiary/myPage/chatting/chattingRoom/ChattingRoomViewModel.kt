package com.anji.babydiary.myPage.chatting.chattingRoom

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.chatting.ChattingDao
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class ChattingRoomViewModel (val database: ChattingDao, val userIdxOne:Long, application: Application) : AndroidViewModel(application) {

    var chatData = database.selectByReceiver(userIdxOne)

    var message = MutableLiveData<String>()
    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    init {

    }

    fun chatData(receiverIdx:Long) {
        uiScope.launch { getChatData(receiverIdx) }

    }

    suspend fun getChatData(receiverIdx:Long) {
        withContext(Dispatchers.IO) {
            //chatData.postValue(database.selectByReceiver(receiverIdx))
        }
    }

    suspend fun insertData(chat:Chatting) {
        withContext(Dispatchers.IO) {
            database.insert(chat)
            message.postValue("")
        }
    }

    fun onSendClick(msg:CharSequence) {
        message.value = msg.toString()
        var chat = Chatting()
        chat.userIdxOne = userIdxOne
        chat.isMyMessage = true
        chat.msgText = msg.toString()
        chat.msgTime = System.currentTimeMillis()

        uiScope.launch {
            insertData(chat)
        }
    }

}


