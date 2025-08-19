package com.example.stockinfo.graph

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockinfo.db.StockInfo
import com.example.stockinfo.db.StockInfosRepository
import com.example.stockinfo.home.DialogUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GraphScreenViewModel(private val stockInfosRepository: StockInfosRepository) : ViewModel(){

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val graphUiState: StateFlow<GraphUiState> =
        stockInfosRepository.getAllStockInfoStream().map { GraphUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GraphUiState()
            )

    //=====================================================

    fun addStockInfoByOne(dialogUiState: DialogUiState): Unit{

        viewModelScope.launch {
            Log.d("DEBUG", "  " + dialogUiState.stockInfo.stockName )
            if(dialogUiState.stockInfo.stockID != 0L &&
                dialogUiState.stockInfo.stockName.isNotEmpty()
            ){
                stockInfosRepository.insertStockInfos(dialogUiState.stockInfo)
            }else{
                Log.d("DEBUG", "存入我的最愛失敗")
            }
        }

    }
}

/**
 * Ui State for HomeScreen
 */
data class GraphUiState(val stockInfoList: List<StockInfo> = listOf())

