package com.example.stockinfo.db

import kotlinx.coroutines.flow.Flow

interface StockInfosRepository {
    fun getAllStockInfoStream(): Flow<List<StockInfo>>

    fun getStockInfoStream(id: Int): Flow<StockInfo?>

    fun isDataExist(stockID: Long): Int

    suspend fun deleteByStockID(stockID: Long): Int

    suspend fun insertStockInfos(stockInfo: StockInfo)

    suspend fun deleteStockInfos(stockInfo: StockInfo)

    suspend fun updateStockInfo(stockInfo: StockInfo)
}
