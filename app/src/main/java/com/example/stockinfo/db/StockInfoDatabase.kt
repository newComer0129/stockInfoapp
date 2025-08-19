package com.example.stockinfo.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StockInfo::class], version = 1, exportSchema = false)
abstract class StockInfoDatabase : RoomDatabase() {

    abstract fun stockInfoDao(): StockInfoDao

    companion object {
        @Volatile
        private var Instance: StockInfoDatabase? = null

        fun getDatabase(context: Context): StockInfoDatabase {
            Log.d("DEBUG", "getDatabase()")
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Log.d("DEBUG", "synchronized()")
                Room.databaseBuilder(context, StockInfoDatabase::class.java, "stockinfo_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}