package com.samuelokello.shopspot.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.samuelokello.shopspot.ui.navigation.Screens


@Composable
fun topBarManager(
    currentRoute: String?,
    navigateToCart: () -> Unit,
): TopBarConfig {
    return when (currentRoute) {
        Screens.Home.route -> TopBarConfig(
            title = "ShopEZ",
            topBarType = TopBarType.CenterAligned,
            actions = {
                IconButton(onClick = navigateToCart) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Cart")
                }
            }
        )

        Screens.Checkout.route -> TopBarConfig(
            title = "Cart",
            topBarType = TopBarType.CenterAligned,
            showBackIcon = true
        )

        Screens.Profile.route -> TopBarConfig(
            title = "",
            topBarType = TopBarType.Regular,
        )

        "${Screens.ProductDetailsScreen.route}/{productId}" -> TopBarConfig(
            title = "",
            topBarType = TopBarType.CenterAligned,
            actions = {
                IconButton(
                    onClick = navigateToCart
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Add to favourite")
                }
            },
            showBackIcon = true
        )
//        Screens.Search.route -> TopBarConfig(
//            title = "",
//            topBarType = TopBarType.CenterAligned,
//            showBackIcon = false,
//        )
        Screens.Favourite.route -> TopBarConfig(
            title = "",
            topBarType = TopBarType.CenterAligned,
            showBackIcon = false
        )

        else -> TopBarConfig(
            title = "",
            topBarType = TopBarType.Regular
        )
    }
}
