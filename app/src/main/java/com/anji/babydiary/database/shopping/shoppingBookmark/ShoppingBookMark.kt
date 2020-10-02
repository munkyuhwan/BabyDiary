package com.anji.babydiary.database.shopping.shoppingBookmark

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_shopping_bookmark")
data class ShoppingBookMark (
    @PrimaryKey(autoGenerate = true)
    val idx:Long = 0L,
    @ColumnInfo(name = "shopping_idx")
    var shoppingIdx:Long=0L,
    @ColumnInfo(name = "user_idx")
    var userIdx:Long = 0L
)