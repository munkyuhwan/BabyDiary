package com.anji.babydiary.database.tip

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.profile.Profiles

@Database(entities = [Tips::class, Profiles::class], version = 5, exportSchema = false)
abstract class TipsDatabase:RoomDatabase() {

    abstract val database: TipsDao

    companion object {

        @Volatile
        private var INSTANCE: TipsDatabase? = null
        fun getInstance(context:Context): TipsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TipsDatabase::class.java, "tips")
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