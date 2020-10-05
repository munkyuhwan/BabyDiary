package com.anji.babydiary.database.tip.tipsComment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_tip_comment")
data class TipsComment(
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "tip_idx")
    var tipIdx:Long = 0L,
    @ColumnInfo(name = "user_idx")
    var userIdx:Long = 0L,
    @ColumnInfo(name="comment_text")
    var commentText:String = "",
    @ColumnInfo(name="regi_date")
    var regiDate:Long = System.currentTimeMillis()
)