package com.anji.babydiary.database.follow

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FollowDao{

    @Insert
    fun insert(follow:Follow)

    @Query("SELECT  * FROM tbl_follow WHERE followee_idx= :key")
    fun selectFollower(key:Long):LiveData<List<Follow>>

    @Query("SELECT  * FROM tbl_follow WHERE follower_idx= :key")
    fun selectFollowee(key:Long):LiveData<List<Follow>>

    @Query("SELECT  * FROM tbl_follow WHERE followee_idx= :followee AND follower_idx= :follower")
    fun checkFollow(followee:Long, follower:Long):LiveData<List<Follow>>


    @Query("DELETE FROM tbl_follow WHERE follower_idx= :follower AND followee_idx= :followee")
    fun deleteAllByFollowe(followee:Long, follower:Long)


    @Query("DELETE FROM tbl_follow")
    fun deletAll()

}