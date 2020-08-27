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

    @Query("SELECT * FROM tbl_daily_check WHERE insert_year = :year AND insert_month = :month AND insert_date = :date ORDER BY insert_hour, insert_min")
    fun selectByDate(year:Int, month:Int, date:Int):LiveData<DailyCheck>

    @Query("SELECT * FROM tbl_daily_check WHERE category= :category AND insert_year= :year AND insert_month= :month AND insert_date = :date")
    fun selectByCategory(category:Int, year: Int, month: Int, date: Int):MutableLiveData<DailyCheck>

    @Query("UPDATE tbl_daily_check SET value_one= :valueOne, value_two= :valueTwo  WHERE category= :category AND insert_year= :year AND insert_month= :month AND insert_date = :date")
    fun update(valueOne:String, valueTwo:String, category:Int, year: Int, month: Int, date: Int)
    /*
        //update ================================================================================
        @Query("UPDATE tbl_daily_check SET weight = :weight, insert_hour = :hour, insert_min=:min  WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateWeight( weight:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET height = :height, insert_hour = :hour, insert_min=:min WHERE insert_year= :year  AND insert_month= :month AND insert_date = :date")
        fun updateHeight( height:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET head = :head, insert_hour = :hour, insert_min=:min  WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateHead( head:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET breast_feed_left = :breastFeedLeft,  breast_feed_right = :breastFeedRight, insert_hour = :hour, insert_min=:min  WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateBreastFeed( breastFeedLeft:String, breastFeedRight:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET breast_pump_feed_left = :left,  breast_pump_feed_right = :right, insert_hour = :hour, insert_min=:min  WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateBreastPumpFeed( left:String, right:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET breast_pump_save_left = :left,  breast_pump_save_right = :right, insert_hour = :hour, insert_min=:min  WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateBreastSaveFeed( left:String, right:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET powder = :powder, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updatePowder( powder:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET food = :food, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateFood( food:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET sub_food = :subFood, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updatesubFood( subFood:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET diaper = :diaper, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateDiaper( diaper:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET sleep = :sleep, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateSleep( sleep:Int, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET bath = :bath, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateBath( bath:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET play = :play, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updatePlay( play:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET hospital = :hospital, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateHospital( hospital:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET temperature = :temperature, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateTemperature( temperature:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET pill = :pill, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updatePill( pill:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET injection = :injection, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateInjection( injection:String, year: Int, month: Int, date: Int, hour:Int, min:Int)

        @Query("UPDATE tbl_daily_check SET etc = :etc, insert_hour = :hour, insert_min=:min   WHERE insert_year= :year AND insert_month= :month AND insert_date = :date")
        fun updateEtc( etc:String, year: Int, month: Int, date: Int, hour:Int, min:Int)
        */
    @Query("DELETE FROM tbl_daily_check")
    fun delete();
}