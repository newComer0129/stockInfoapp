package com.example.stockinfo.db

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class OfflineStockInfosRepository(private val stockInfoDao: StockInfoDao): StockInfosRepository {
    override fun getAllStockInfoStream(): Flow<List<StockInfo>> = stockInfoDao.getAllStockInfos()

    override fun getStockInfoStream(id: Int): Flow<StockInfo?> = stockInfoDao.getStockInfo(id)

    override fun isDataExist(stockid: Long) = stockInfoDao.isDataExist(stockid)

    override suspend fun deleteByStockID(stockID: Long) = stockInfoDao.deleteByStockID(stockID)

    override suspend fun insertStockInfos(stockInfo: StockInfo) = stockInfoDao.insert(stockInfo)

    override suspend fun deleteStockInfos(stockInfo: StockInfo) = stockInfoDao.delete(stockInfo)

    override suspend fun updateStockInfo(stockInfo: StockInfo) = stockInfoDao.update(stockInfo)

}