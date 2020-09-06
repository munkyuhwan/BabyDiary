package com.anji.babydiary.database.alarm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.chatting.ChattingDao
import com.anji.babydiary.database.chatting.ChattingDatabase

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {

    abstract val database: AlarmDao
    companion object {

        @Volatile
        private var INSTANCE: AlarmDatabase? = null
        fun getInstance(context: Context): AlarmDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AlarmDatabase::class.java, "baby_db")
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