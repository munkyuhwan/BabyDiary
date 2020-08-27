package com.anji.babydiary.database.dailyCheck

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_daily_check")
data class DailyCheck (
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "user_idx")
    var user_idx:Long = 0L,

    @ColumnInfo(name="category")
    var category:Int = 0,
    @ColumnInfo(name = "value_one")
    var valueOne:String = "",
    @ColumnInfo(name = "value_two")
    var valueTwo:String = "",

    @ColumnInfo(name = "insert_year")
    var year:Int = 0,
    @ColumnInfo(name = "insert_month")
    var month:Int = 0,
    @ColumnInfo(name = "insert_date")
    var date:Int = 0,
    @ColumnInfo(name = "insert_hour")
    var hour:Int = 0,
    @ColumnInfo(name = "insert_min")
    var minute:Int = 0


    /*
    @ColumnInfo(name = "weight")
    var weight:String = "",
    @ColumnInfo(name = "height")
    var height:String = "",
    @ColumnInfo(name = "head")
    var head:String = "",
    @ColumnInfo(name = "breast_feed_left")
    var breast_feed_left:Long=0L,
    @ColumnInfo(name = "breast_feed_right")
    var breast_feed_right:Long=0L,
    @ColumnInfo(name = "breast_pump_feed_left")
    var breast_pump_feed_left:Long=0L,
    @ColumnInfo(name = "breast_pump_feed_right")
    var breast_pump_feed_right:Long=0L,
    @ColumnInfo(name = "breast_pump_save_left")
    var breast_pump_save_left:Long=0L,
    @ColumnInfo(name = "breast_pump_save_right")
    var breast_pump_save_right:Long=0L,
    @ColumnInfo(name = "powder")
    var powder:String="",
    @ColumnInfo(name = "food")
    var food:String="",
    @ColumnInfo(name = "sub_food")
    var sub_food:String="",
    @ColumnInfo(name = "diaper")
    var diaper:String="",
    @ColumnInfo(name = "sleep")
    var sleep:Int=0,
    @ColumnInfo(name = "bath")
    var bath:String="",
    @ColumnInfo(name = "play")
    var play:String="",
    @ColumnInfo(name = "hospital")
    var hospital:String="",
    @ColumnInfo(name = "temperature")
    var temperature:String="",
    @ColumnInfo(name = "pill")
    var pill:String="",
    @ColumnInfo(name = "injection")
    var injection:String="",
    @ColumnInfo(name = "etc")
    var etc:String="",
    */

)