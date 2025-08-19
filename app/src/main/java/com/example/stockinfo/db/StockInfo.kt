package com.example.stockinfo.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stockinfos")
data class StockInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stockID: Long,
    val stockName: String
)

