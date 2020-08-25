package com.anji.babydiary.database.likes


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_likes")
data class Likes (
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "feed_idx")
    var feed_idx:Long=0L,
    @ColumnInfo(name = "user_idx")
    var user_idx:Long=0L,
    @ColumnInfo(name = "write_date")
    var date:Long=0L

)