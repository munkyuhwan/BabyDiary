package com.anji.babydiary.database.profile

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfileDao{

    @Insert
    fun insert(profile:Profile)

    @Query("SELECT * FROM tbl_profile ")
    fun selectAll():LiveData<List<Profile>>

    @Query("SELECT * FROM tbl_profile WHERE idx= :key")
    fun selectProfile(key:Long):Profile


    @Query("DELETE FROM tbl_profile")
    fun deleteAll()

    @Query("DELETE FROM tbl_profile WHERE idx= :key")
    fun deleteKey(key: Long)

}