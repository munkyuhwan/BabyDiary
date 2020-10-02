package com.anji.babydiary.database.comments

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface CommentsDao {

    @Insert
    fun insert(comment:Comments)

    @Query("SELECT * FROM tbl_comments WHERE feed_idx= :feedIdx")
    fun selectAllByFeedIdx(feedIdx:Long):LiveData<List<Comments>>

    @Query ("DELETE FROM tbl_comments WHERE idx=:idx")
    fun deleteByIdx(idx:Long)

    //@Query("SELECT * FROM tbl_comments WHERE feed_idx= :feedIdx")
    //fun selectAllByFeedIdxWithProfile(feedIdx:Long):LiveData<List<CommentWithProfile>>



}