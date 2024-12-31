package com.samuelokello.shopspot.util

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

sealed class TopBarType {
    data object CenterAligned : TopBarType()
    data object Regular : TopBarType()
}

data class TopBarConfig(
    val title: String,
    val topBarType: TopBarType,
    val actions: @Composable (RowScope.() -> Unit)? = null,
    val showBackIcon: Boolean = false
)
