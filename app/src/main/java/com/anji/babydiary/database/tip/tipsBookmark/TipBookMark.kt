package com.anji.babydiary.database.tip.tipsBookmark

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_tip_bookmark")
data class TipBookMark (
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "tip_idx")
    var tipIdx:Long=0L,
    @ColumnInfo(name = "user_idx")
    var userIdx:Long = 0L
)