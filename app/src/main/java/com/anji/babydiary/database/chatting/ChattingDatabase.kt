package com.anji.babydiary.database.chatting

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.comments.CommentsDatabase

@Database(entities = [Chatting::class], version = 3, exportSchema = false)
abstract class ChattingDatabase:RoomDatabase() {

    abstract val database:ChattingDao
    companion object {

        @Volatile
        private var INSTANCE:ChattingDatabase? = null
        fun getInstance(context: Context):ChattingDatabase{
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, ChattingDatabase::class.java, "tbl_chatting")
                        .fallbackToDestructiveMigration()
                        .build()
                }

                INSTANCE = instance
                return instance

            }



        }


    }


}