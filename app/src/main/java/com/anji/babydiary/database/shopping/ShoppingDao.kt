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
    fun selectAll():List<Shopping>


    @Query("SELECT * FROM tbl_product WHERE product_title LIKE '%' || :keyword || '%'")
    fun selectAllByKeyword(keyword:String):List<Shopping>

    @Query("SELECT * FROM tbl_product WHERE product_type =  :keyword ")
    fun selectAllByType(keyword:String):List<Shopping>




    @Query("DELETE FROM tbl_product")
    fun deleteAll()


}