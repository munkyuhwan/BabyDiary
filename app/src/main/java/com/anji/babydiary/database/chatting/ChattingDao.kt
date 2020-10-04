package com.anji.babydiary.database.chatting

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ChattingDao {

    @Insert
    fun insert(chatting:Chatting)

    @Query("SELECT * FROM tbl_chatting GROUP BY user_idx_one ORDER BY idx DESC")
    fun selectAll():LiveData<List<Chatting>>

    @Query("SELECT * FROM tbl_chatting WHERE chatting_room= :key")
    fun selectByChattingRoom(key:Long):List<Chatting>

    @Query("SELECT * FROM tbl_chatting WHERE user_idx_one= :userIdxOne AND text LIKE '%' || :key || '%' ")
    fun searchByChattingRoom(userIdxOne:Long, key:String):List<Chatting>


    @Query("DELETE FROM tbl_chatting")
    fun deleteAll()

    @Query ("SELECT * FROM tbl_chatting WHERE user_idx_one= :receiver ORDER BY idx ASC")
    fun selectByReceiver(receiver:Long):LiveData<List<Chatting>>


    @Query ("SELECT * FROM tbl_chatting WHERE user_idx_one= :receiver ORDER BY idx ASC")
    fun selectAllWthUser(receiver:Long):List<Chatting>



}