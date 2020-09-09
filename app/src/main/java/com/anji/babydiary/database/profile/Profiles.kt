package com.anji.babydiary.database.profile

import androidx.room.*
import com.anji.babydiary.database.mainFeed.MainFeed

@Entity(tableName = "tbl_profile")
data class Profiles (
    @PrimaryKey(autoGenerate = true)
    var idx:Long = 0L,
    @ColumnInfo(name = "id")
    var id:Int =0,
    @ColumnInfo(name = "profile_img")
    var img:String ="",
    @ColumnInfo(name = "profile_img_tmp")
    var imgTmp:String ="",
    @ColumnInfo(name = "name")
    var name:String ="",
    @ColumnInfo(name = "pass")
    var pass:String ="",
    @ColumnInfo(name = "introduce")
    var introduce:String ="",
    @ColumnInfo(name = "follower")
    var follower:Long=0L,
    @ColumnInfo(name = "following")
    var following:Long=0L
)