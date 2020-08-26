package com.anji.babydiary.database.dailyCheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DailyCheckDao  {

    @Insert
    fun insert(dailyCheck: DailyCheck)

    @Query("SELECT * FROM tbl_daily_check WHERE user_idx = :key")
    fun selectAll(key:Int):LiveData<List<DailyCheck>>

    @Query("SELECT * FROM tbl_daily_check WHERE insert_year = :year AND insert_month = :month AND insert_date = :date ORDER BY insert_hour, insert_min")
    fun selectByDate(year:Int, month:Int, date:Int):DailyCheck

    @Query("UPDATE tbl_daily_check SET weight = :weight WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
    fun updateWeight(weight:Long, year: Int, month: Int, date: Int)
}