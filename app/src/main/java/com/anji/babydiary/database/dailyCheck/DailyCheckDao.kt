package com.anji.babydiary.database.dailyCheck

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DailyCheckDao  {

    @Insert
    fun insert(dailyCheck: DailyCheck)

    @Query("SELECT * FROM tbl_daily_check WHERE user_idx = :key")
    fun selectAll(key:Int):LiveData<List<DailyCheck>>

    @Query("SELECT * FROM tbl_daily_check WHERE user_idx= :pk AND insert_year = :year AND insert_month = :month AND insert_day = :date ORDER BY insert_hour, insert_min")
    fun selectByDate(pk:Long, year:Int, month:Int, date:Int):List<DailyCheck>



}