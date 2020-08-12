package com.anji.babydiary.database.shopping

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_tip")
data class Tip(
    @PrimaryKey(autoGenerate = true)
    var idx:Long = 0L,
    @ColumnInfo(name = "tip_text")
    var text:String = "",
    @ColumnInfo(name = "tip_user")
    var user:String = "",
    @ColumnInfo(name = "like_cnt")
    var cnt:String = "",
    @ColumnInfo(name = "tip_image")
    var imgDir: String = "",
    @ColumnInfo(name="tip_category")
    var category:String = ""
)