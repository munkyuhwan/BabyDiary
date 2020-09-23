package com.anji.babydiary.database.tip.tipsComment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface TipsCommentDao {
    @Insert
    fun insert(tipsComment: TipsComment)


    @Query("SELECT * FROM tbl_tip_comment")
    fun selectAll():LiveData<List<TipsComment>>


    @Query("SELECT * FROM tbl_tip_comment WHERE tip_idx = :tipPk ")
    fun selectByTipOnlyIdx(tipPk:Long):LiveData<List<TipsComment>>


    @Query("UPDATE tbl_tip_comment SET comment_text= :text WHERE idx = :idx ")
    fun updateTipComment(idx:Long, text:String)

    @Query("DELETE FROM tbl_tip_comment WHERE idx=:idx")
    fun deleteTipComment(idx:Long)

/*
    @Transaction
    @Query("SELECT * FROM tbl_tip_comment WHERE tip_idx = :tipPk ")
    fun selectByTipIdx(tipPk:Long):LiveData<List<TipsCommentWithUser>>
*/
}