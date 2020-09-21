package com.anji.babydiary.database.bookmark

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.chatting.Chatting
import com.anji.babydiary.database.chatting.ChattingDao
import com.anji.babydiary.database.profile.Profiles


@Database(entities = [BookMark::class], version = 1, exportSchema = false)
abstract class BookMarkDatabase: RoomDatabase() {

    abstract val database: BookMarkDao
    companion object {

        @Volatile
        private var INSTANCE:BookMarkDatabase? = null
        fun getInstance(context: Context):BookMarkDatabase{
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, BookMarkDatabase::class.java, "bookmark")
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