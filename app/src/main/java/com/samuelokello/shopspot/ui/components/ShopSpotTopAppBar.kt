package com.samuelokello.shopspot.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopSpotTopAppBar(config: TopBarConfig, navController: NavController) {
    when (config.topBarType) {
        TopBarType.CenterAligned -> {
            CenterAlignedTopAppBar(
                title = { Text(config.title, style = appBarTextStyle) },
                navigationIcon = {if (config.showBackIcon){
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
                },
                actions = { config.actions?.invoke(this) }
            )
        }
        TopBarType.Regular -> {
            TopAppBar(
                title = { Text(config.title, style = appBarTextStyle) },
                navigationIcon = {},
                actions = { config.actions?.invoke(this) }
            )
        }
    }
}
val appBarTextStyle
    @Composable get() = MaterialTheme.typography.titleLarge.copy(
        color = Color.Black,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    )
