package com.anji.babydiary.database.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_alarm")
class Alarm (
    @PrimaryKey(autoGenerate = true)
    val idx:Long


)