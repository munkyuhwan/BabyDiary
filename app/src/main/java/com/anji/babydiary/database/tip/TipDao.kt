package com.anji.babydiary.database.shopping

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TipDao {

    @Insert
    fun insert(tip:Tip)

    @Query("SELECT * FROM tbl_tip")
    fun selectAll():LiveData<List<Tip>>

    @Query("DELETE FROM tbl_tip")
    fun deleteAll()


}