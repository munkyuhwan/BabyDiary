package com.anji.babydiary.database.shopping

import androidx.room.*

@Entity(tableName = "tbl_tips")
data class Tips(
    @PrimaryKey(autoGenerate = true)
    var idx:Long = 0L,
    @ColumnInfo(name = "tip_text")
    var text:String = "",
    @ColumnInfo(name = "user_idx")
    var user_idx:Long = 0L,
    @ColumnInfo(name = "tip_user")
    var user:String = "",
    @ColumnInfo(name = "like_cnt")
    var cnt:Int = 0,
    @ColumnInfo(name = "tip_image")
    var imgDir: String = "",
    @ColumnInfo(name="tip_category")
    var category:String = ""
)