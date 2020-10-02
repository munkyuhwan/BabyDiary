package com.anji.babydiary.database.shopping.shoppingBookmark

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anji.babydiary.database.mainFeed.MainFeed
import com.anji.babydiary.database.tip.tipsBookmark.TipBookMark

@Dao
interface ShoppingBookMarkDao {

    @Insert
    fun insert(tipBookMark: ShoppingBookMark)

    @Query("SELECT * FROM tbl_shopping_bookmark ")
    fun selectAll():LiveData<List<ShoppingBookMark>>

    @Query("SELECT * FROM tbl_shopping_bookmark WHERE idx= :idx")
    fun selectByIdx(idx:Long):ShoppingBookMark

    @Query("SELECT * FROM tbl_shopping_bookmark WHERE user_idx= :userIdx")
    fun selectByUserIdx(userIdx:Long):List<ShoppingBookMark>

    @Query("SELECT * FROM tbl_shopping_bookmark WHERE shopping_idx= :tipIdx")
    fun selectByBookmarkIdx(tipIdx:Long):List<ShoppingBookMark>

    @Query("SELECT * FROM tbl_shopping_bookmark WHERE user_idx= :userIdx AND shopping_idx= :tipIdx")
    fun selectByBookmarkAndUser(userIdx:Long, tipIdx:Long):List<ShoppingBookMark>

    @Query("DELETE FROM tbl_shopping_bookmark WHERE user_idx= :userIdx AND shopping_idx= :tipIdx ")
    fun deleteBookmark(userIdx:Long, tipIdx:Long)


    @Query("SELECT * FROM tbl_shopping_bookmark WHERE user_idx= :userIdx AND shopping_idx= :shoppingIdx")
    fun selectByShoppingAndUser(userIdx:Long, shoppingIdx:Long):List<ShoppingBookMark>


}