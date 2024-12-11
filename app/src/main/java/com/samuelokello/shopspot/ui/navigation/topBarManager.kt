package com.samuelokello.shopspot.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.shopspot.ui.components.TopBarConfig
import com.samuelokello.shopspot.ui.components.TopBarType


@Composable
fun topBarManager(currentRoute: String?, navController: NavController): TopBarConfig {
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
            topBarType = TopBarType.CenterAligned,
            showBackIcon = true
        )
        Screens.Profile.route -> TopBarConfig(
            title = "",
            topBarType = TopBarType.Regular,
        )
        "${Screens.ProductDetailsScreen.route}/{productJson}" -> TopBarConfig(
            title = "",
            topBarType = TopBarType.CenterAligned,
            actions = {
                IconButton(
                    onClick = {

                }, modifier = Modifier.padding( end= 10.dp)) {

                    IconButton(
                        onClick = {

                        }
                    ){
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Add to favourite")
                    }
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
