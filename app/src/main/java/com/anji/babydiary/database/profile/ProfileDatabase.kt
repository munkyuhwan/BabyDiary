package com.anji.babydiary.database.profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Profiles::class], version = 6, exportSchema = false)
abstract class ProfileDatabase:RoomDatabase()  {

    abstract val database: ProfileDao

    companion object {

        @Volatile
        private var INSTANCE: ProfileDatabase? = null
        fun getInstance(context: Context): ProfileDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, ProfileDatabase::class.java, "baby_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }


}
