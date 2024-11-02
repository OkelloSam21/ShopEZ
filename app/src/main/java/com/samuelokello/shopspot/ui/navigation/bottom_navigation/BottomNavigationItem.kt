package com.samuelokello.shopspot.ui.navigation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCartCheckout
import androidx.compose.ui.graphics.vector.ImageVector
import com.samuelokello.shopspot.ui.navigation.Screens


data class BottomNavigationItem(
    val label: String = "", val icon: ImageVector = Icons.Default.Home, val route: String = "") {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem("Home",Icons.Default.Home, Screens.Products.route),
            BottomNavigationItem("Search",Icons.Filled.Search, Screens.Search.route),
            BottomNavigationItem("Favourite",Icons.Rounded.Favorite, Screens.Favourite.route),
            BottomNavigationItem("Profile",Icons.Rounded.Person, Screens.Profile.route),
        )
    }
}