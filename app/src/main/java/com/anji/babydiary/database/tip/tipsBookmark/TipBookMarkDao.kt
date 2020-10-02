package com.anji.babydiary.database.tip.tipsBookmark

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TipBookMarkDao {

    @Insert
    fun insert(tipBookMark: TipBookMark)

    @Query("SELECT * FROM tbl_tip_bookmark ")
    fun selectAll():LiveData<List<TipBookMark>>

    @Query("SELECT * FROM tbl_tip_bookmark WHERE idx= :idx")
    fun selectByIdx(idx:Long):TipBookMark

    @Query("SELECT * FROM tbl_tip_bookmark WHERE user_idx= :userIdx")
    fun selectByUserIdx(userIdx:Long):List<TipBookMark>

    @Query("SELECT * FROM tbl_tip_bookmark WHERE tip_idx= :tipIdx")
    fun selectByTipIdx(tipIdx:Long):List<TipBookMark>

    @Query("SELECT * FROM tbl_tip_bookmark WHERE user_idx= :userIdx AND tip_idx= :tipIdx")
    fun selectByTipAndUser(userIdx:Long, tipIdx:Long):List<TipBookMark>

    @Query("DELETE FROM tbl_tip_bookmark WHERE user_idx= :userIdx AND tip_idx= :tipIdx ")
    fun deleteBookmark(userIdx:Long, tipIdx:Long)

}