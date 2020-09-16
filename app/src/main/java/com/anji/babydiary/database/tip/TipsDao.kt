package com.anji.babydiary.database.tip

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TipsDao {

    @Insert
    fun insert(tip: Tips)

    @Query("SELECT * FROM tbl_tips")
    fun selectAll():LiveData<List<Tips>>

    @Query("SELECT * FROM tbl_tips")
    fun selectWithUser():LiveData<List<Tips>>

    @Query("SELECT * FROM tbl_tips WHERE tip_category= :cat")
    fun selectByCategory(cat:String):LiveData<List<Tips>>

    @Query("DELETE FROM tbl_tips")
    fun deleteAll()

    @Query("UPDATE tbl_tips SET like_cnt = :cnt")
    fun updateLike(cnt:Int)

}