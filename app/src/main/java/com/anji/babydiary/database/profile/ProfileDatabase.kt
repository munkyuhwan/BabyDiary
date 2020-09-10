package com.anji.babydiary.database.profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Profiles::class], version = 10, exportSchema = false)
abstract class ProfileDatabase:RoomDatabase()  {

    abstract val database: ProfileDao

    companion object {

        @Volatile
        private var INSTANCE: ProfileDatabase? = null
        fun getInstance(context: Context): ProfileDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, ProfileDatabase::class.java, "baby.db")
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
