package com.example.stockinfo.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.stockinfo.db.StockInfo

@Composable
fun AddStockInfoDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit
) {
    Log.d("DEBUG", "AddStockInfoDialog")
    var stockInfoString = "" //記錄點選股票代號

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Log.d("DEBUG", "Dialog")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                //選取的股票代號回傳
                stockInfoString =
                    autoCompleteTextField(
                        modifier = Modifier.fillMaxWidth(),
                        StockIDList,
                    )
                Log.d("DEBUG", "gggg  stockInfoString : ${stockInfoString}")

                Text(
                    text = "新增觀察清單",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("取消")
                    }
                    TextButton(
                        onClick = {
                            onConfirmation(stockInfoString)
                            onDismissRequest()
                                  },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("新增")
                    }
                }
            }
        }
    }
}

@Composable
fun autoCompleteTextField(
    modifier: Modifier,
    items: List<String>,
): String
{
    var textFieldValue by remember { mutableStateOf(TextFieldValue(""))}
    var suggestions by remember { mutableStateOf(items) }
    var stockInfoString by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        TextField(
            value = textFieldValue,
            onValueChange = {newValue ->
                textFieldValue = newValue
                suggestions =
                    if (newValue.text.isNotEmpty()){
                        items.filter {
                            it.contains(newValue.text, ignoreCase = true)
                        }
                    }
                    else{
                        Log.d("DEBUG", "不符合以下格式  2330 台積電")
                        emptyList()
                    }
            },
            leadingIcon = {Icon(Icons.Filled.Search, null)},
            placeholder =  {Text("輸入股票代號")},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
        )

        if(suggestions.isNotEmpty() && textFieldValue.text.isNotEmpty()){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .heightIn(max = 200.dp)
                    .padding(18.dp)
            ) {
                items(suggestions){suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                textFieldValue = TextFieldValue(
                                    text = suggestion,
                                    selection = TextRange(suggestion.length)
                                )
                                stockInfoString = suggestion
                                suggestions = emptyList()
                                Log.d("DEBUG", "${stockInfoString}___")
                            }
                    )
                }
            }
        }
        Log.d("DEBUG", "${stockInfoString}111____")
    }
    Log.d("DEBUG", "${stockInfoString}000____")
    return stockInfoString
}

@Composable
fun removeStockInfoDialog(
    stockID: Long,
    onDismissRequest: () -> Unit,
    onConfirmation: (Long) -> Unit
) {
    Log.d("DEBUG", "removeStockInfoDialog")
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Log.d("DEBUG", "Dialog")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "確認移除？",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("取消")
                    }
                    TextButton(
                        onClick = {
                            onConfirmation(stockID)
                            onDismissRequest()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("刪除")
                    }
                }
            }
        }
    }
}