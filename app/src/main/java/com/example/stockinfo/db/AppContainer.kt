package com.example.stockinfo.db

import android.content.Context
import android.util.Log

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val stockInfosRepository: StockInfosRepository
}


/**
 * [AppContainer] implementation that provides instance of [OfflineStockInfossRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [StockInfosRepository]
     */
    override val stockInfosRepository: StockInfosRepository by lazy {
        Log.d("DEBUG", "stockInfosRepository")
        OfflineStockInfosRepository(StockInfoDatabase.getDatabase(context).stockInfoDao())
    }
}
