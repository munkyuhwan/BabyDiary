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

    @Query("SELECT * FROM tbl_daily_check ")
    fun selectAll():LiveData<List<DailyCheck>>

    @Query("SELECT * FROM tbl_daily_check WHERE user_idx= :idx AND insert_year = :year AND insert_month = :month AND insert_date = :date ORDER BY insert_hour, insert_min")
    fun selectByDate(year:Int, month:Int, date:Int, idx:Long):List<DailyCheck>

    @Query("SELECT * FROM tbl_daily_check WHERE user_idx= :idx AND category= :category AND insert_year= :year AND insert_month= :month AND insert_date = :date")
    fun selectByCategory(category:Int, year: Int, month: Int, date: Int, idx: Long):DailyCheck

    @Query("UPDATE tbl_daily_check SET value_one= :valueOne, value_two= :valueTwo  WHERE  user_idx= :idx AND category= :category AND insert_year= :year AND insert_month= :month AND insert_date = :date")
    fun update(valueOne:String, valueTwo:String, category:Int, year: Int, month: Int, date: Int, idx:Long)


    @Query("UPDATE tbl_daily_check SET value_one= :valueOne, value_two= :valueTwo  WHERE  idx = :idx")
    fun updateByIdx(valueOne:String, valueTwo:String, idx:Long)

    @Query("DELETE FROM tbl_daily_check")
    fun delete();
}