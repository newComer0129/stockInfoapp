package com.example.stockinfo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StockInfoDao {

    //@Query("SELECT * from stockinfos ORDER BY stockName ASC")
    @Query("SELECT * from stockinfos ORDER BY stockID ASC")
    fun getAllStockInfos(): Flow<List<StockInfo>>

    @Query("SELECT * from stockinfos WHERE id = :id")
    fun getStockInfo(id: Int): Flow<StockInfo>

    @Query("SELECT COUNT(*) FROM stockinfos WHERE stockid = :stockid")
    fun isDataExist(stockid: Long): Int

    @Query("DELETE FROM stockinfos WHERE stockid = :stockid")
    fun deleteByStockID(stockid: Long): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stockInfo: StockInfo)

    @Update
    suspend fun update(stockInfo: StockInfo)

    @Delete
    suspend fun delete(stockInfo: StockInfo)
}
