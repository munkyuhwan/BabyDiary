package com.anji.babydiary.database.shopping

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.profile.Profiles

@Database(entities = [Tips::class], version = 3, exportSchema = false)
abstract class TipsDatabase:RoomDatabase() {

    abstract val database:TipsDao

    companion object {

        @Volatile
        private var INSTANCE:TipsDatabase? = null
        fun getInstance(context:Context):TipsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TipsDatabase::class.java, "baby.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }

}