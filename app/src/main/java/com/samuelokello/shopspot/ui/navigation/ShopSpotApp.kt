package com.samuelokello.shopspot.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.samuelokello.shopspot.data.Product
import com.samuelokello.shopspot.repository.ProductRepository
import com.samuelokello.shopspot.ui.order.OrderPlacedScreen
import com.samuelokello.shopspot.ui.checkout.CheckoutScreen
import com.samuelokello.shopspot.ui.favourite.FavouriteScreen
import com.samuelokello.shopspot.ui.navigation.bottom_navigation.BottomNavigationItem
import com.samuelokello.shopspot.ui.productdetails.ProductDetailsScreen
import com.samuelokello.shopspot.ui.home.HomeScreen
import com.samuelokello.shopspot.ui.home.ProductViewModel
import com.samuelokello.shopspot.ui.home.ProductViewModelFactory
import com.samuelokello.shopspot.ui.profile.ProfileScreen
import com.samuelokello.shopspot.ui.search.SearchScreen
import com.samuelokello.shopspot.ui.theme.primaryLight

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopSpotApp() {
    var navigationSelectedItem by rememberSaveable { mutableIntStateOf(0) }
    val navController = rememberNavController()

    // Create ProductRepository instance
    val productRepository = remember { ProductRepository() }

    // Create ViewModelFactory
    val factory = remember { ProductViewModelFactory() }

    val viewModel: ProductViewModel = viewModel(factory = factory)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ShopEZ",
                        style = MaterialTheme.typography.titleLarge
                            .copy(
                                color = primaryLight,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold
                            )
                    )
                },
                actions = {
                    IconButton(
                        onClick = {navController.navigate(Screens.Checkout.route)}
                    ) {
                        Icon(Icons.Default.AddShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent
            ) {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        modifier = Modifier,
                        label = {
                            Text(
                                text = item.label
                            )
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Products.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screens.Products.route) {
                HomeScreen(
                    viewModel = viewModel,
                    navigateToItemDetails = {
                        navController.navigate("${Screens.ProductDetailsScreen} + /product")
                    }
                )
            }
            composable(Screens.Checkout.route) {
                // Checkout screen composable
                CheckoutScreen(navController = navController, viewModel = viewModel)
            }
            composable(Screens.Search.route) {
                SearchScreen(modifier = Modifier)
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
            composable(Screens.ProductDetailsScreen.route) { backStackEntry->
                val productJson = backStackEntry.arguments?.getString("product")
                val product = Gson().fromJson(productJson, Product::class.java)
                ProductDetailsScreen(product = product)
            }
        }
    }
}
