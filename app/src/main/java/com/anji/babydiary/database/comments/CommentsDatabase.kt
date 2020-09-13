package com.anji.babydiary.database.comments

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase

@Database(entities = [Comments::class], version = 2, exportSchema = false)
abstract class CommentsDatabase:RoomDatabase()  {

    abstract val database:CommentsDao

    companion object {
        @Volatile
        private var INSTANCE: CommentsDatabase? = null
        fun getInstance(context: Context): CommentsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, CommentsDatabase::class.java, "baby.db")
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