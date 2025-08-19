package com.example.stockinfo

import android.app.Application
import android.util.Log
import com.example.stockinfo.db.AppContainer
import com.example.stockinfo.db.AppDataContainer
import com.example.stockinfo.db.StockInfoDatabase

class StockInfoApplication : Application() {
    val database: StockInfoDatabase by lazy { StockInfoDatabase.getDatabase(this) }
    /**
    * AppContainer instance used by the rest of classes to obtain dependencies
    */
    lateinit var container: AppContainer

    override fun onCreate() {
        Log.d("DEBUG", "onCreate()")
        super.onCreate()
        container = AppDataContainer(this)
    }
}