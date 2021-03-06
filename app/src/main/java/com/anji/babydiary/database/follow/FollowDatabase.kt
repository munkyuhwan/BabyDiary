package com.anji.babydiary.database.follow

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Follow::class], version = 6, exportSchema = false)
abstract class FollowDatabase():RoomDatabase() {

    abstract val database:FollowDao

    companion object {

        @Volatile
        private var INSTANCE:FollowDatabase? = null
        fun getInstance(context:Context):FollowDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, FollowDatabase::class.java, "baby.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance;
            }
        }

    }



}