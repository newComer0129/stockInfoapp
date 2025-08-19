@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.stockinfo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.stockinfo.component.StockInfoTabRow
import com.example.stockinfo.navigation.StockInfoNavHost
import com.example.stockinfo.ui.theme.StockInfoTheme
import androidx.navigation.NavHostController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate()")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockInfoTheme {
                StockInfoApp()
            }
        }
    }
}

@Composable
fun StockInfoApp(){
    Log.d("mainActivity", "StockInfoApp()")
    var currentScreen: StockInfoDestination by remember { mutableStateOf(HomeDestination) }
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("This is a demo") },
            )
        },
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets){
                StockInfoTabRow(
                    allScreens = stockInfoTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        }
    ) { innerPadding ->
        StockInfoNavHost(navController, modifier = Modifier.padding(innerPadding))
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ){
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StockInfoTheme {
        StockInfoApp()
    }
}