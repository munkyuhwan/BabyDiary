package com.anji.babydiary.database.chatting

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChattingDao {

    @Insert
    fun insert(chatting:Chatting)

    @Query("SELECT * FROM tbl_chatting WHERE chatting_room= :key")
    fun selectByChattingRoom(key:Long):List<Chatting>

    @Query("DELETE FROM tbl_chatting")
    fun deleteAll()

}