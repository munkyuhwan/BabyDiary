package com.anji.babydiary.database.follow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_follow")
class Follow (
    @PrimaryKey(autoGenerate = true)
    val idx:Long=0L,
    @ColumnInfo(name = "followee")
    var followee:String="",
    @ColumnInfo(name = "followee_idx")
    var followee_idx:Long=0L,
    @ColumnInfo(name = "follower")
    var follower:String="",
    @ColumnInfo(name = "follower_idx")
    var follower_idx:Long=0L

)
