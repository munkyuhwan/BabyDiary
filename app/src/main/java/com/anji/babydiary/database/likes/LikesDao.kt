package com.anji.babydiary.database.likes


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface LikesDao {

    @Insert
    fun insert(like: Likes)

    @Query("SELECT * FROM tbl_likes WHERE feed_idx= :feedIdx")
    fun selectAllByFeedIdx(feedIdx:Long):LiveData<List<Likes>>


    @Query("SELECT COUNT(*) AS cnt FROM tbl_likes WHERE feed_idx= :feedIdx")
    fun selectCountByFeedIdx(feedIdx:Long):Int


}