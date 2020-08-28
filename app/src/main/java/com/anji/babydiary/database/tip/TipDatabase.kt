package com.anji.babydiary.database.shopping

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tip::class], version = 5, exportSchema = false)
abstract class TipDatabase:RoomDatabase() {

    abstract val database:TipDao

    companion object {

        @Volatile
        private var INSTANCE:TipDatabase? = null
        fun getInstance(context:Context):TipDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, TipDatabase::class.java, "tbl_tip")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }

}