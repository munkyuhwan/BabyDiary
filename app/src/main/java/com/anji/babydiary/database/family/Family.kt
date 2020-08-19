package com.anji.babydiary.database.family

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_family")
class Family (
    @PrimaryKey(autoGenerate = true)
    var idx:Long = 0L,
    @ColumnInfo(name = "user_idx")
    var user_idx:Long = 0L,
    @ColumnInfo(name = "family_title")
    var family_title:String = "",
    @ColumnInfo(name = "family_name")
    var family_name:String = ""

)