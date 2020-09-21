package com.anji.babydiary.database.bookmark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookMarkDao {

    @Insert
    fun insert(bookMark: BookMark)

    @Query("SELECT * FROM tbl_bookmark WHERE user_idx= :idx GROUP BY feed_idx")
    fun selectAllByUserIdx(idx:Long):List<BookMark>



}