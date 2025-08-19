package com.example.stockinfo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 設定Destination
 */

interface StockInfoDestination {
    val icon: ImageVector
    val route: String
    val screen: @Composable () -> Unit
}

/**
 * Rally app navigation destinations
 */

object HomeDestination : StockInfoDestination {
    override val icon = Icons.Default.Home
    override val route = "home"
    override val screen: @Composable () -> Unit = {}
}

object GraphDestination : StockInfoDestination {
    override val icon = Icons.Default.Favorite
    override val route = "graph"
    override val screen: @Composable () -> Unit = {}
}

object SuggestionDestination : StockInfoDestination {
    override val icon = Icons.Default.Face
    override val route = "suggestion"
    override val screen: @Composable () -> Unit = {}
}


//主畫面下方三個tab
val stockInfoTabRowScreens = listOf(HomeDestination, GraphDestination, SuggestionDestination)

