package com.anji.babydiary.database.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_alarm")
data class Alarm (
    @PrimaryKey(autoGenerate = true)
    val idx:Long=0L,
    @ColumnInfo(name = "type")
    var type:Long=0L,
    @ColumnInfo(name = "title")
    var title:String="",
    @ColumnInfo(name = "msg")
    var msg:String=""


)