package com.anji.babydiary.database.shopping

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingDao {

    @Insert
    fun insert(shopping:Shopping)

    @Query("SELECT * FROM tbl_product")
    fun selectAll():LiveData<List<Shopping>>

    @Query("DELETE FROM tbl_product")
    fun deleteAll()


}