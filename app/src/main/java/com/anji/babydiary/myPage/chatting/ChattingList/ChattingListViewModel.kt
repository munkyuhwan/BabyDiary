package com.anji.babydiary.myPage.chatting.chattingList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anji.babydiary.common.CommonCode
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.chatting.ChattingDao
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ChattingListViewModel(val database: ChattingDao, application: Application) : AndroidViewModel(application) {

    val allData = database.selectAll()

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {

    }

    


}

class OnChattingListClick(val clickListener:(id:Long)->Unit) {
    fun onClick(chatting: Chatting) = clickListener(chatting.userIdxOne)
}

/*
class OnChattingListClick(val chattingClickListener:(resultId:Long)->Unit) {
    fun onClick(chatting: Chatting) = chattingClickListener(chatting.idx)
}
*/
