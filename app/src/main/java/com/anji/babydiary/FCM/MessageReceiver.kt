package com.anji.babydiary.FCM

import android.util.Log
import com.anji.babydiary.database.alarm.Alarm
import com.anji.babydiary.database.alarm.AlarmDatabase
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.chatting.ChattingDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MessageReceiver:FirebaseMessagingService() {


    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)

        Log.e("msg","=============================================================")
        Log.e("msg","${msg.data}")
        Log.e("msg","=============================================================")
        msg?.let {

            Log.e("msg","=============================================================")
            Log.e("msg","${it.data}")
            Log.e("msg","${it.data.size}")
            Log.e("msg","=============================================================")
            if (it.data.size > 0){
                setNotification(it.data.get("title").toString(), it.data.get("body").toString(), it.data.get("userIdx").toString().toLong(), it.data.get("userName").toString(), it.data.get("type").toString()  )
            }

        }

    }

    fun setNotification(title:String, body:String, userIdx:Long, userName:String, type:String) {
        when(type) {
            "chat" -> {
                updateDatabase(userIdx, body, userName)
            }
            "alarm" -> {
                updateAlarm(userIdx, title, body, userName)
            }
        }
    }

    fun updateAlarm(userIdx: Long,title:String, msg:String, userName: String) {
        val database = AlarmDatabase.getInstance(applicationContext).database
        var alarm = Alarm()
        alarm.title = title
        alarm.msg = msg

        database.insert(alarm)

    }

    fun updateDatabase(userIdx: Long, msg:String, userName: String) {
        val database = ChattingDatabase.getInstance(applicationContext).database
        var chatting = Chatting()
        chatting.msgText = msg
        chatting.responderName = userName
        chatting.userIdxOne = userIdx
        chatting.msgTime = System.currentTimeMillis()

        database.insert(chatting)
    }

}



