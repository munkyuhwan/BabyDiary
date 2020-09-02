package com.anji.babydiary.database.chatting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_chatting")
data class Chatting(
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "chatting_room")
    var roomIdx:Long = 0L,
    @ColumnInfo(name="is_my_message")
    var isMyMessage:Boolean = false,

    @ColumnInfo(name = "user_idx_one")
    var userIdxOne:Long = 0L,
    @ColumnInfo(name = "user_idx_two")
    var userIdxTwo:Long = 0L,



    @ColumnInfo(name = "receiver_img")
    var responderImg:Long = 0L,
    @ColumnInfo(name = "receiver_name")
    var responderName:String = "",
    @ColumnInfo(name = "text")
    var msgText:String = "",
    @ColumnInfo(name = "msg_time")
    var msgTime:Long = 0
)