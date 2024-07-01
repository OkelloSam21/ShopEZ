package com.samuelokello.shopspot.ui.bottom_nsvigation

import androidx.compose.ui.graphics.vector.ImageVector

object BottomNavigationItem {
    data class Products(
        val title: String,
        val icon: ImageVector
    )

    data class Checkout(
        val title: String,
        val icon: ImageVector
    )
}