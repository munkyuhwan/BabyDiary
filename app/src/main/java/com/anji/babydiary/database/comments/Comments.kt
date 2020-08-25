package com.anji.babydiary.database.comments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_comments")
data class Comments (
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "feed_idx")
    var feedIdx:Long=0L,
    @ColumnInfo(name = "comment_idx")
    var commentIdx:Long=0L,
    @ColumnInfo(name = "comment_text")
    var commentText:String="",
    @ColumnInfo(name = "depth")
    var depth:Long=0L,
    @ColumnInfo(name = "user_idx")
    var userIdx:Long=0L,
    @ColumnInfo(name = "write_date")
    var date:Long=0L

)