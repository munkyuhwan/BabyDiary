package com.anji.babydiary.database.mainFeed

import androidx.room.*
import com.anji.babydiary.database.profile.Profiles
import java.sql.Date


@Entity(
    //foreignKeys = [
    //    ForeignKey(entity = Profiles::class, parentColumns = ["user_idx"], childColumns = ["idx"])
    //],
    tableName = "tbl_main_feed")
data class MainFeed(
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "user_id")
    var userId:String = "",

    @ColumnInfo(name = "user_idx")
    var userIdx:Long = 0L,

    @ColumnInfo(name = "feed_title")
    var title:String = "",
    @ColumnInfo(name = "feed_text")
    var toSpouser:String = "",

    @ColumnInfo(name = "location")
    var location:String = "",

    @ColumnInfo(name = "height")
    var height:Long = 0L,
    @ColumnInfo(name = "weight")
    var weight:Long = 0L,
    @ColumnInfo(name = "head")
    var head:Long = 0L,

    //@ColumnInfo(name="write_data")
    //var date:Long = System.currentTimeMillis(),

    @ColumnInfo(name = "year")
    var year:Int = 0,
    @ColumnInfo(name = "month")
    var month:Int = 0,
    @ColumnInfo(name = "date")
    var date:Int = 0,
    @ColumnInfo(name="time_milli")
    var timeMilli:Long = 0,

    @ColumnInfo(name = "like_cnt")
    var likeCnt:Long = 0L,
    @ColumnInfo(name="img_dir")
    var imgDir:String = "",
    @ColumnInfo(name="img_tmp_dir")
    var imgTmpDir:String = "",

    @ColumnInfo(name = "feed_type")
    var feedType:String = ""

    )

