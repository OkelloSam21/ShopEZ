package com.samuelokello.shopspot.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.samuelokello.shopspot.data.TopBarConfig
import com.samuelokello.shopspot.data.TopBarType


@Composable
fun TopBarManager(currentRoute: String?, navController: NavController): TopBarConfig {
    return when (currentRoute) {
        Screens.Home.route -> TopBarConfig(
            title = "ShopEZ",
            topBarType = TopBarType.CenterAligned,
            actions = {
                IconButton(onClick = { navController.navigate(Screens.Checkout.route) }) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Cart")
                }
            }
        )
        Screens.Checkout.route -> TopBarConfig(
            title = "Checkout",
            topBarType = TopBarType.Regular
        )
        Screens.Profile.route -> TopBarConfig(
            title = "Profile",
            topBarType = TopBarType.Regular
        )
        "${Screens.ProductDetailsScreen.route}/{productJson}" -> TopBarConfig(
            title = "Product Details",
            topBarType = TopBarType.Regular
        )
        else -> TopBarConfig(
            title = "ShopEZ",
            topBarType = TopBarType.Regular
        )
    }
}
