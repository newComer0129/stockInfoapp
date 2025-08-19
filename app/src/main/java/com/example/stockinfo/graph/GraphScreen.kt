package com.example.stockinfo.graph

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockinfo.AppViewModelProvider
import com.example.stockinfo.db.StockInfo
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphScreen(modifier: Modifier = Modifier,
                graphScreenViewModel: GraphScreenViewModel
                = viewModel(factory = AppViewModelProvider.Factory),
) {
    Log.d("GraphScreen", "GraphScreen()1")
    val graphUiState by graphScreenViewModel.graphUiState.collectAsState()
    var stockItemClick  = remember { mutableStateOf(false) }
    var chooseItem = remember { mutableStateOf(StockInfo(0, 2330L, "台積電")) }
    Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    )
    {
        Log.d("DEBUG", "chooseItem" + chooseItem.value.stockName)
        val url = "https://www.google.com/finance/quote/"+ chooseItem.value.stockID.toString()+":TPE"
        ComposableWebView(url)
        StockInfoList(
            stockInfoList = graphUiState.stockInfoList,
            onItemClick = {it->
                chooseItem.value = it
            },
            contentPadding = PaddingValues(5.dp),
        )
        Log.d("GraphScreen", "GraphScreen()2")
    }
}

@Composable
private fun StockInfoList(
    stockInfoList: List<StockInfo>,
    onItemClick: (StockInfo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = stockInfoList, key = { it.id }) { item ->
            StockInfoItem(item = item,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { onItemClick(item) })
        }
    }
}

@Composable
private fun StockInfoItem(
    item: StockInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = item.stockID.toString(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.padding(5.dp))
                Text(
                    text = item.stockName,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Composable
fun ComposableWebView(url: String, modifier: Modifier = Modifier){
    Log.d("DEBUG", "ComposableWebView")
    val context = LocalContext.current
    AndroidView(
        factory = {context->
            WebView(context).apply {
                Log.d("DEBUG", "ComposableWebView factory ")
                //設定webview 支援javascript
                settings.javaScriptEnabled = true
                layoutParams = ViewGroup.LayoutParams(700, 1400)
                webViewClient = WebViewClient()
                Log.d("DEBUG", "ComposableWebView url: " + url)
                //載入url
                loadUrl(url)
            }
        },
        update = {webview->
            Log.d("DEBUG", "ComposableWebView udate url: " + url)
            Log.d("DEBUG", "ComposableWebView webview.url: " + webview.url)

            //更新頁面
            if(webview.url != url){
                Log.d("DEBUG", "ComposableWebView update url: loadurl " + webview.url)
                webview.loadUrl(url)
            }
        }
    )
}

data class CandleData
    (val open: Short, val high: Short, val low: Short, val close: Short){
}