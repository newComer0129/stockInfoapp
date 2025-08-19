package com.example.stockinfo.component

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stockinfo.StockInfoDestination
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple



@Composable
fun StockInfoTabRow(
    allScreens: List<StockInfoDestination>,
    onTabSelected: (StockInfoDestination) -> Unit,
    currentScreen: StockInfoDestination
){
    Log.d("StockInfoTabRow", "StockInfoTabRow")
    Surface(
        modifier = Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween){
            allScreens.forEach { screen ->
                StockInfoTab(
                    text = screen.route,
                    icon = screen.icon,
                    onSelected = {
                        Log.d("StockInfoTab", "onSelected")
                        onTabSelected(screen) },
                    selected = currentScreen == screen
                )

            }
        }
    }

}

@Composable
fun StockInfoTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Icon(imageVector = icon, contentDescription = text)
    }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f
private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100
