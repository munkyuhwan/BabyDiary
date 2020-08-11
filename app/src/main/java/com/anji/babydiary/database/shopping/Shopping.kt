package com.anji.babydiary.database.shopping

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_product")
data class Shopping(
    @PrimaryKey(autoGenerate = true)
    var idx:Long = 0L,
    @ColumnInfo(name = "product_title")
    var title:String = "",
    @ColumnInfo(name = "product_price")
    var price:String = "",
    @ColumnInfo(name = "product_url")
    var url:String = "",
    @ColumnInfo(name = "product_image")
    var imgDir: String = ""
)