package com.anji.babydiary.database.event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_event")
data class Event (
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "img")
    var imgDir:String = "",
    @ColumnInfo(name = "title")
    var title:String = "",
    @ColumnInfo(name = "text")
    var text:String = ""
)