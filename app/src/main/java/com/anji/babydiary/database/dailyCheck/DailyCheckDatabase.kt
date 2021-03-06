package com.anji.babydiary.database.dailyCheck

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.event.EventDatabase

@Database(entities = [DailyCheck::class], version = 5, exportSchema = false)
abstract class DailyCheckDatabase ():RoomDatabase() {
    abstract val database:DailyCheckDao

    companion object {

        @Volatile
        private var INSTANCE: DailyCheckDatabase? = null
        fun getInstance(context: Context): DailyCheckDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, DailyCheckDatabase::class.java, "baby.db")
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
