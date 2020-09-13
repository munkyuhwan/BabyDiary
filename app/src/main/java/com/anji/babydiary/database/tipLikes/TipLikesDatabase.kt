package com.anji.babydiary.database.tipLikes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.likes.LikesDatabase

@Database(entities = [TipLikes::class], version = 1, exportSchema = false)
abstract class TipLikesDatabase:RoomDatabase() {
    abstract val database:TipLikesDao

    companion object {
        @Volatile
        private var INSTANCE:TipLikesDatabase? = null
        fun getInstance(context: Context):TipLikesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TipLikesDatabase::class.java, "baby.db")
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