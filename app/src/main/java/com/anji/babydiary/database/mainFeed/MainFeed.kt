package com.anji.babydiary.database.mainFeed

import androidx.room.*
import com.anji.babydiary.database.profile.Profiles
import java.sql.Date


@Entity(
    //foreignKeys = [
    //    ForeignKey(entity = Profiles::class, parentColumns = ["idx"], childColumns = ["user_idx"])
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

    @ColumnInfo(name="write_data")
    var date:Long = System.currentTimeMillis(),
    @ColumnInfo(name = "like_cnt")
    var likeCnt:Long = 0L,
    @ColumnInfo(name="img_dir")
    var imgDir:String = "",
    @ColumnInfo(name="img_tmp_dir")
    var imgTmpDir:String = ""

    )

