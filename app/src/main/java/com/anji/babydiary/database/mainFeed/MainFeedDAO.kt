package com.anji.babydiary.database.mainFeed

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MainFeedDAO{

    @Insert
    fun insert(mainFeed: MainFeed)

    @Query("SELECT * FROM tbl_main_feed")
    fun selectAll(): LiveData<List<MainFeed>>

    @Query("SELECT * FROM tbl_main_feed WHERE idx= :key")
    fun selectSingle(key:Long): LiveData<MainFeed>

    @Query("DELETE FROM tbl_main_feed")
    fun deleteAll()

}