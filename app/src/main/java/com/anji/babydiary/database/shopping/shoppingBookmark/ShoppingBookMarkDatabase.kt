package com.anji.babydiary.database.shopping.shoppingBookmark

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.tip.TipsDao
import com.anji.babydiary.database.tip.TipsDatabase

@Database(entities = [ShoppingBookMark::class], version = 1, exportSchema = false)
abstract class ShoppingBookMarkDatabase:RoomDatabase() {

    abstract val database: ShoppingBookMarkDao

    companion object {

        @Volatile
        private var INSTANCE: ShoppingBookMarkDatabase? = null
        fun getInstance(context: Context): ShoppingBookMarkDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, ShoppingBookMarkDatabase::class.java, "shopping_bookmark")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }
}