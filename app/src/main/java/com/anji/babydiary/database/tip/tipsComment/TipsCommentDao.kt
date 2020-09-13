package com.anji.babydiary.database.tip.tipsComment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TipsCommentDao {
    @Insert
    fun insert(tipsComment: TipsComment)


    @Query("SELECT * FROM tbl_tip_comment")
    fun selectAll():LiveData<List<TipsComment>>


    @Query("SELECT * FROM tbl_tip_comment WHERE tip_idx = :tipPk ")
    fun selectByTipIdx(tipPk:Long):LiveData<List<TipsCommentWithUser>>

}