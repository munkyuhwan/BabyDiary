package com.anji.babydiary.database.mainFeed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "tbl_main_feed")
data class MainFeed(
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "user_idx")
    var userId:String = "",
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

    @ColumnInfo(name="write_data")
    var date:Long = System.currentTimeMillis(),
    @ColumnInfo(name = "like_cnt")
    var likeCnt:Long = 0L,
    @ColumnInfo(name="img_dir")
    var imgDir:String = ""

)