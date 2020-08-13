package com.anji.babydiary.database.event

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

    @Insert
    fun insert(event:Event)

    @Query("SELECT * FROM tbl_event")
    fun selectAll():MutableLiveData<List<Event>>

    @Query("DELETE FROM tbl_event")
    fun deleteAll()

}