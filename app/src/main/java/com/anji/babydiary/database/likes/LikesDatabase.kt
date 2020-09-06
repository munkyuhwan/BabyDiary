package com.anji.babydiary.database.likes


import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.comments.Comments
import com.anji.babydiary.database.comments.CommentsDao
import com.anji.babydiary.database.dailyCheck.DailyCheckDatabase

@Database(entities = [Likes::class], version = 1, exportSchema = false)
abstract class LikesDatabase:RoomDatabase()  {

    abstract val database: LikesDao

    companion object {
        @Volatile
        private var INSTANCE: LikesDatabase? = null
        fun getInstance(context: Context): LikesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, LikesDatabase::class.java, "baby_db")
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