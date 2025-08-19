package com.example.stockinfo

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stockinfo.graph.GraphScreenViewModel
import com.example.stockinfo.home.HomeScreenViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for SearchScreenViewModel
        initializer {
            HomeScreenViewModel(stockInfoApplication().container.stockInfosRepository)
        }
        initializer {
            GraphScreenViewModel(stockInfoApplication().container.stockInfosRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [StockinfoApplication].
 */
fun CreationExtras.stockInfoApplication(): StockInfoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as StockInfoApplication)
