package com.anji.babydiary.database.family

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Family::class], version = 1, exportSchema = false)
abstract class FamilyDatabase:RoomDatabase()  {

    abstract val database:FamilyDao

    companion object {

        @Volatile
        private var INSTANCE:FamilyDatabase? = null
        fun getInstance(context:Context):FamilyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, FamilyDatabase::class.java, "tbl_family").fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }

    }

}