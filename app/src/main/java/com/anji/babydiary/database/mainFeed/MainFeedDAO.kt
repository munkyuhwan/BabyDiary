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
    fun selectSingle(key:Long):LiveData<MainFeed>

    @Query("SELECT * FROM tbl_main_feed WHERE user_idx= :key GROUP BY date, month, date ")
    fun selectDates(key:Long):List<MainFeed>

    @Query("SELECT * FROM tbl_main_feed WHERE user_idx= :key")
    fun selectByUserIdx(key:Long): List<MainFeed>


    @Query("SELECT * FROM tbl_main_feed WHERE user_idx= :key AND date = :date AND month = :month AND year = :year")
    fun selectByUserIdxANDDate(key:Long, date:Int, month:Int, year:Int): LiveData<List<MainFeed>>


    @Query("UPDATE tbl_main_feed SET like_cnt = :cnt WHERE idx = :pk ")
    fun updateLike(cnt:Long, pk:Long)

    @Query("DELETE FROM tbl_main_feed")
    fun deleteAll()


    @Query("SELECT * FROM tbl_main_feed WHERE feed_type = :type")
    fun selectAllByType(type:String): LiveData<List<MainFeed>>

    /*
    @Transaction
    @Query("SELECT * FROM tbl_main_feed")
    fun selectWithProfile(): LiveData< List<FeedWithUser> >


    @Transaction
    @Query("SELECT * FROM tbl_main_feed")
    fun selectWithProfileMain(): LiveData<List<FeedWithUser>>


    @Transaction
    @Query("SELECT * FROM tbl_main_feed GROUP BY year, month ,date")
    fun selectWithProfileMainGroupBy(): List<FeedWithUser>



    @Transaction
    @Query("SELECT * FROM tbl_main_feed WHERE feed_type = :type")
    fun selectAllByType(type:String): LiveData<List<FeedWithUser>>

     */




}