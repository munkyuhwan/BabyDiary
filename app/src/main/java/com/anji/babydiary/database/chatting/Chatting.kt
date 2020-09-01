package com.anji.babydiary.database.chatting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_chatting")
data class Chatting(
    @PrimaryKey
    val idx:Long = 0L,
    @ColumnInfo(name = "chatting_room")
    var roomIdx:Long = 0L,
    @ColumnInfo(name = "receiver_idx")
    var receiverIdx:Long = 0L,
    @ColumnInfo(name = "receiver_img")
    var receiverImg:Long = 0L,
    @ColumnInfo(name = "receiver_name")
    var receiverName:String = "",
    @ColumnInfo(name = "text")
    var msgText:String = "",
    @ColumnInfo(name = "msg_time")
    var msgTime:Int = 0
)