package com.samuelokello.shopspot.data

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

sealed class TopBarType {
    object CenterAligned : TopBarType()
    object Regular : TopBarType()
}

data class TopBarConfig(
    val title: String,
    val topBarType: TopBarType,
    val actions: @Composable (RowScope.() -> Unit)? = null
)
