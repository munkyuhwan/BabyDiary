package com.anji.babydiary.database.tipLikes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TipLikesDao{

    @Insert
    fun insert(tipLikes:TipLikes)

    @Query("SELECT * FROM tbl_tip_likes ")
    fun selectAll():LiveData<List<TipLikes>>

    @Query("SELECT * FROM tbl_tip_likes WHERE tip_idx = :tipIdx ")
    fun selectAllByIdx(tipIdx:Long):LiveData<List<TipLikes>>


}