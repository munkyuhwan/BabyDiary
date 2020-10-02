package com.anji.babydiary.database.tip.tipsBookmark

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.tip.TipsDao
import com.anji.babydiary.database.tip.TipsDatabase

@Database(entities = [TipBookMark::class], version = 1, exportSchema = false)
abstract class TipBookMarkDatabase:RoomDatabase() {

    abstract val database: TipBookMarkDao

    companion object {

        @Volatile
        private var INSTANCE: TipBookMarkDatabase? = null
        fun getInstance(context: Context): TipBookMarkDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TipBookMarkDatabase::class.java, "tips_bookmark")
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