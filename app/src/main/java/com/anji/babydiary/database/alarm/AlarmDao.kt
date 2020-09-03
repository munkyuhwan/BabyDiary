package com.anji.babydiary.database.alarm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlarmDao {

    @Insert
    fun insert(alarm:Alarm)

    @Query("SELECT * FROM tbl_alarm")
    fun selectAll():LiveData<List<Alarm>>


}