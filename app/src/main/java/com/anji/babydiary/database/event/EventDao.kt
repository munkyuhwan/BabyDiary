package com.anji.babydiary.database.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

    @Insert
    fun insert(event:Event)

    @Query("SELECT * FROM tbl_event")
    fun selectAll():List<Event>


    @Query("SELECT * FROM tbl_event")
    fun selectCheckAll():LiveData<List<Event>>

    @Query("DELETE FROM tbl_event")
    fun deleteAll()

    @Query("SELECT * FROM tbl_event WHERE idx= :key")
    fun selectIdx(key:Long):LiveData<Event>

    @Query("SELECT * FROM tbl_event WHERE title LIKE '%' || :key || '%' OR text  LIKE '%' || :key || '%'  ")
    fun selectByKeyword(key:String):List<Event>

}