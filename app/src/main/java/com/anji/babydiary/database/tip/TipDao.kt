package com.anji.babydiary.database.shopping

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TipDao {

    @Insert
    fun insert(tip:Tip)

    @Query("SELECT * FROM tbl_tip")
    fun selectAll():LiveData<List<Tip>>

    @Query("DELETE FROM tbl_tip")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM tbl_tip WHERE user_idx= :pk")
    fun selectTipWithUser(pk:Long):List<TipAndProfile>


}