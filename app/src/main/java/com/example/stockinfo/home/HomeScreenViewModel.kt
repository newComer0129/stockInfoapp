package com.example.stockinfo.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockinfo.db.StockInfo
import com.example.stockinfo.db.StockInfosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val stockInfosRepository: StockInfosRepository) : ViewModel(){

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /**
     * Holds current stockInfo ui state on dialog
     */

    val homeUiState: StateFlow<HomeUiState> =
        stockInfosRepository.getAllStockInfoStream().map { HomeUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    /*
     * 確認股票代號是否已經在我的最愛
     */
    fun checkStockInfoExistByStockID(stockInfos: List<StockInfo>, stockID: Long): Boolean{
        Log.d("DEBUG", "zzz1")
        var exist = true
        //檢查我的最愛名單是否為空
        if(stockInfos.isEmpty()){
            Log.d("DEBUG", "zzz2 ")
            exist = false
            return exist
        }

        //檢查是否已經存在我的最愛名單內
        for (items in stockInfos){
            Log.d("DEBUG", "zzz3")
            if(stockID == items.stockID){
                Log.d("DEBUG", "zzz stockID : ${stockID}")
                Log.d("DEBUG", "zzz items.stockID : ${items.stockID}")
                Log.d("DEBUG", "zzz ================================")
                exist = true
                break
            }
            else{
                exist = false
            }
        }
        Log.d("DEBUG", "exist : ${exist}")
        return exist
    }

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

    fun removeStockInfoByStockID(stockID: Long): Unit{
        CoroutineScope(Dispatchers.IO).launch {
            if(stockID != 0L
            ){
                stockInfosRepository.deleteByStockID(stockID)
            }else{
                Log.d("DEBUG", "刪除失敗")
            }
        }
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val stockInfoList: List<StockInfo> = listOf())

/**
 * Represents Ui State for an Item.
 */
data class DialogUiState(
    val stockInfo: StockInfo// = StockInfo(0, 0, "")
)

/**
 * Long轉型Int
 */
fun Long.toInt(): Int? {
    return if (this >= Int.MIN_VALUE && this <= Int.MAX_VALUE) {
        toInt()
    } else {
        null
    }
}