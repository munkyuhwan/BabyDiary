package com.anji.babydiary.database.shopping

import android.net.Uri
import androidx.room.*
import com.anji.babydiary.database.profile.Profiles

@Entity(
    tableName = "tbl_tip"
    )
data class Tip(
    @PrimaryKey(autoGenerate = true)
    var idx:Long = 0L,
    @ColumnInfo(name = "tip_text")
    var text:String = "",
    @ColumnInfo(name = "user_idx")
    var user_idx:Long = 0L,
    @ColumnInfo(name = "tip_user")
    var user:String = "",
    @ColumnInfo(name = "like_cnt")
    var cnt:String = "",
    @ColumnInfo(name = "tip_image")
    var imgDir: String = "",
    @ColumnInfo(name="tip_category")
    var category:String = ""
)