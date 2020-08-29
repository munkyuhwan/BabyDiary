package com.anji.babydiary.database.profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anji.babydiary.database.shopping.ShoppingDao
import com.anji.babydiary.database.shopping.ShoppingDatabase


@Database(entities = [Profiles::class], version = 4, exportSchema = false)
abstract class ProfileDatabase:RoomDatabase()  {

    abstract val database: ProfileDao

    companion object {

        @Volatile
        private var INSTANCE: ProfileDatabase? = null
        fun getInstance(context: Context): ProfileDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, ProfileDatabase::class.java, "tbl_profile")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }


}
