package com.anji.babydiary.database.profile

import androidx.lifecycle.LiveData
import androidx.room.*
import com.anji.babydiary.database.mainFeed.FeedWithUser

@Dao
interface ProfileDao{

    @Insert
    fun insert(profile:Profiles)

    @Query("SELECT * FROM tbl_profile ")
    fun selectAll():LiveData<List<Profiles>>

    @Query("SELECT * FROM tbl_profile ")
    fun selectAllTmp():List<Profiles>

    @Query("SELECT * FROM tbl_profile ")
    fun checkUserData():LiveData<List<Profiles>>

    @Query("SELECT * FROM tbl_profile WHERE idx= :key ORDER BY idx DESC")
    fun selectProfile(key:Long):Profiles


    @Query("DELETE FROM tbl_profile")
    fun deleteAll()

    @Query("DELETE FROM tbl_profile WHERE idx= :key")
    fun deleteKey(key: Long)

    @Query("UPDATE tbl_profile SET profile_img= :img, name= :name, pass= :pass, introduce= :introduce  WHERE idx= :pk")
    fun updateQuery(img:String, name:String, pass:String, introduce:String, pk:Long)
    @Update
    fun update(profile:Profiles)


}