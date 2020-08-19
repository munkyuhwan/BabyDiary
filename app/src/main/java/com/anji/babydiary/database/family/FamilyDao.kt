package com.anji.babydiary.database.family

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FamilyDao  {

    @Insert
    fun insert(family:Family)

    @Query("SELECT * FROM tbl_family")
    fun selectAll():LiveData<List<Family>>

    @Query("SELECT * FROM tbl_family WHERE user_idx= :key")
    fun selectByUser(key:Long):LiveData<List<Family>>

}