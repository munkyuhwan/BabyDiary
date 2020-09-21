package com.anji.babydiary.database.bookmark

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_bookmark")
data class BookMark(
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "user_idx")
    var user_idx:Long = 0L,
    @ColumnInfo(name = "feed_idx")
    var feed_idx:Long = 0L
)