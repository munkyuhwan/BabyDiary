package com.anji.babydiary.database.mainFeed

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.profile.Profiles

@Database(entities = [MainFeed::class, Profiles::class], version = 16, exportSchema = false)
abstract class MainFeedDatabase:RoomDatabase() {

    abstract val database:MainFeedDAO

    companion object{
        @Volatile
        private var INSTANCE:MainFeedDatabase? = null

        fun getInstance(context: Context):MainFeedDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, MainFeedDatabase::class.java, "mainfeed")
                        //.allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }

}