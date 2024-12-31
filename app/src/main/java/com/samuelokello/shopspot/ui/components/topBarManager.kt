package com.samuelokello.shopspot.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuelokello.shopspot.ui.navigation.Screens
import com.samuelokello.shopspot.util.TopBarConfig
import com.samuelokello.shopspot.util.TopBarType


@Composable
fun topBarManager(
    currentRoute: String?,
    navigateToCart: () -> Unit,
): TopBarConfig {
    return when (currentRoute) {
        Screens.Register.route -> TopBarConfig(
            title = "",
            topBarType = TopBarType.CenterAligned,
            showBackIcon = true,
            actions = { }
        )
        Screens.Login.route -> TopBarConfig(
            title = "",
            topBarType = TopBarType.CenterAligned,
            showBackIcon = true,
            actions = {}
        )
        Screens.ForgotPassword.route -> TopBarConfig(
            title = "Forgot Password",
            topBarType = TopBarType.CenterAligned,
            showBackIcon = true,
            actions = {

            }
        )
        Screens.Home.route -> TopBarConfig(
            title = "ShopEZ",
            topBarType = TopBarType.CenterAligned,
            actions = {
                IconButton(onClick = navigateToCart) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Cart")
                }
            }
        )

        Screens.Cart.route -> TopBarConfig(
            title = "My Cart",
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
