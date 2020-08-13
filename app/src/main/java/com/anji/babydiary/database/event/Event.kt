package com.anji.babydiary.database.event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_event")
class Event (
    @PrimaryKey
    val idx:Long = 0L,
    @ColumnInfo(name = "img")
    val imgDir:String = "",
    @ColumnInfo(name = "title")
    val title:String = "",
    @ColumnInfo(name = "text")
    val text:String = ""
)