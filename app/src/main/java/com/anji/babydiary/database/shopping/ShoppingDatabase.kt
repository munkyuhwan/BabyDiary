package com.anji.babydiary.database.shopping

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Shopping::class], version = 4, exportSchema = false)
abstract class ShoppingDatabase:RoomDatabase() {

    abstract val database:ShoppingDao

    companion object {

        @Volatile
        private var INSTANCE:ShoppingDatabase? = null
        fun getInstance(context:Context):ShoppingDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, ShoppingDatabase::class.java, "baby.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }

}