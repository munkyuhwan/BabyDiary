package com.anji.babydiary.database.mainFeed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_main_feed")
data class MainFeed(
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "user_idx")
    var userId:String = "",
    @ColumnInfo(name = "feed_title")
    var title:String = "",
    @ColumnInfo(name = "feed_text")
    var text:String = "",
    @ColumnInfo(name="write_data")
    var date:Long = 0L,
    @ColumnInfo(name = "like_cnt")
    var likeCnt:Long = 0L,
    @ColumnInfo(name="img_dir")
    var imgDir:Int = 0
)