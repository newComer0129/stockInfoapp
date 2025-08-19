package com.example.stockinfo.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.stockinfo.GraphDestination
import com.example.stockinfo.HomeDestination
import androidx.navigation.compose.composable
import com.example.stockinfo.SuggestionDestination
import com.example.stockinfo.graph.GraphScreen
import com.example.stockinfo.graph.GraphScreenViewModel
import com.example.stockinfo.home.HomeScreen


/**
 * Provides Navigation graph for the application.
 */
@Composable
fun StockInfoNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(HomeDestination.route){
            Log.d("NavHost", "composable(HomeDestination.route)")
            HomeScreen(
                modifier = modifier,
            )
        }
        composable(GraphDestination.route){
            Log.d("NavHost", "composable(GraphDestination.route)")
            GraphScreen(
                modifier = modifier,
            )
        }
        composable(SuggestionDestination.route){
            Log.d("NavHost", "composable(SuggestionDestination.route)")
        }
    }
}
