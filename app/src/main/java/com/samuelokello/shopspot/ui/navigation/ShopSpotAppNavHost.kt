package com.samuelokello.shopspot.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.samuelokello.shopspot.domain.Product
import com.samuelokello.shopspot.ui.AppViewModelProvider
import com.samuelokello.shopspot.ui.checkout.CheckoutScreen
import com.samuelokello.shopspot.ui.components.ShopSpotTopAppBar
import com.samuelokello.shopspot.ui.favourite.FavouriteScreen
import com.samuelokello.shopspot.ui.home.HomeScreen
import com.samuelokello.shopspot.ui.home.HomeViewModel
import com.samuelokello.shopspot.ui.navigation.bottom_navigation.ShopSpotBottomNavigation
import com.samuelokello.shopspot.ui.order.OrderPlacedScreen
import com.samuelokello.shopspot.ui.productdetails.ProductDetailsScreen
import com.samuelokello.shopspot.ui.profile.ProfileScreen
import com.samuelokello.shopspot.ui.search.SearchScreen

@Composable
fun ShopSpotAppNavHost() {
    val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val topBarConfig = topBarManager(currentRoute.toString(), navController)

    Scaffold(
        topBar = {
            ShopSpotTopAppBar(config = topBarConfig, navController)
        },
        bottomBar = {
            if (currentRoute in setOf(
                    Screens.Home.route,
                    Screens.Profile.route,
                    Screens.Favourite.route,
                    Screens.Search.route
                )) {
                ShopSpotBottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Home.route) {
                HomeScreen(
                    navigateToItemDetails = { productId ->
                        navController.navigate("${Screens.ProductDetailsScreen.route}/$productId")
                    }
                )
            }
            composable(Screens.Checkout.route) {
                CheckoutScreen(navController = navController, viewModel = viewModel)
            }
            composable(Screens.Search.route) {
                SearchScreen(
                    modifier = Modifier,
                    navigateToItemDetails = { product ->
                        navController.navigate("${Screens.ProductDetailsScreen.route}/${product.id}")
                    }
                )
            }
            composable(Screens.Favourite.route) {
                FavouriteScreen(modifier = Modifier)
            }
            composable(Screens.Profile.route) {
                ProfileScreen(modifier = Modifier)
            }
            composable(Screens.OrderPlaced.route) {
                OrderPlacedScreen(navController = navController, viewModel = viewModel)
            }
            composable(
                route = "${Screens.ProductDetailsScreen.route}/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId")
                if (productId != null) {
                    ProductDetailsScreen(productId = productId)
                }
            }
        }
    }
}
