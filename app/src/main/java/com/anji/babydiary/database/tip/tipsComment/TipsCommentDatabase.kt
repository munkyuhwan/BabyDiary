package com.anji.babydiary.database.tip.tipsComment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.profile.Profiles
import com.anji.babydiary.database.tip.TipsDatabase


@Database(entities = [TipsComment::class, Profiles::class], version = 3, exportSchema = false)
abstract class TipsCommentDatabase:RoomDatabase() {
    abstract val database:TipsCommentDao

    companion object {

        @Volatile
        private var INSTANCE: TipsCommentDatabase? = null
        fun getInstance(context: Context): TipsCommentDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TipsCommentDatabase::class.java, "baby.db")
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