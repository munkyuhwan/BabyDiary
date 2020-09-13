package com.anji.babydiary.database.tipLikes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_tip_likes")
data class TipLikes(
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "user_idx")
    var userIdx:Long = 0L,
    @ColumnInfo(name = "tip_idx")
    var tip_idx:Long = 0L
)