package com.anji.babydiary.database.mainFeed

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MainFeedDAO{

    @Insert
    fun insert(mainFeed: MainFeed)

    @Query("SELECT * FROM tbl_main_feed")
    fun selectAll(): LiveData<List<MainFeed>>
    @Query("SELECT * FROM tbl_main_feed WHERE idx= :key")
    fun selectSingle(key:Long):MainFeed


    @Query("SELECT * FROM tbl_main_feed WHERE user_idx= :key")
    fun selectByUserIdx(key:Long): List<MainFeed>

    @Query("UPDATE tbl_main_feed SET like_cnt = :cnt WHERE idx = :pk ")
    fun updateLike(cnt:Long, pk:Long)

    @Query("DELETE FROM tbl_main_feed")
    fun deleteAll()


    @Transaction
    @Query("SELECT * FROM tbl_main_feed")
    fun selectWithProfile(): LiveData< List<FeedWithUser> >


    @Transaction
    @Query("SELECT * FROM tbl_main_feed")
    fun selectWithProfileMain(): List<FeedWithUser>


    @Transaction
    @Query("SELECT * FROM tbl_main_feed WHERE feed_type = :type")
    fun selectAllByType(type:String): List<FeedWithUser>




}