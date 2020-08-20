package com.anji.babydiary.database.follow

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FollowDao{

    @Insert
    fun insert(follow:Follow)

    @Query("SELECT  * FROM tbl_follow WHERE followee= :key")
    fun selectFollower(key:Long):LiveData<List<Follow>>

    @Query("SELECT  * FROM tbl_follow WHERE follower= :key")
    fun selectFollowee(key:Long):LiveData<List<Follow>>

    @Query("DELETE FROM tbl_follow")
    fun deletAll()

}