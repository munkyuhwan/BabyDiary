package com.anji.babydiary.database.event

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.shopping.TipDatabase

@Database(entities = [Event::class], version = 3, exportSchema = false)
abstract class EventDatabase:RoomDatabase() {
    abstract val database:EventDao

    companion object {

        @Volatile
        private var INSTANCE:EventDatabase? = null
        fun getInstance(context: Context): EventDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, EventDatabase::class.java, "tbl_event")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }
    }

}