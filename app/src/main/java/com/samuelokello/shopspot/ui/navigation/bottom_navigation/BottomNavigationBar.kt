package com.samuelokello.shopspot.ui.navigation.bottom_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.samuelokello.shopspot.ui.navigation.Screens
import com.samuelokello.shopspot.ui.productdetails.ProductDetailsScreen
import com.samuelokello.shopspot.ui.products.ProductScreen
import com.samuelokello.shopspot.ui.products.ProductViewModel
import com.samuelokello.shopspot.ui.products.ProductViewModelFactory
import com.samuelokello.shopspot.ui.theme.secondaryContainerLight

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    var navigationSelectedItem by rememberSaveable { mutableIntStateOf(0) }
    val navController = rememberNavController()

    // Create ProductRepository instance
    val productRepository = remember { ProductRepository() }

    // Create ViewModelFactory
    val factory = remember { ProductViewModelFactory(productRepository) }

    val viewModel: ProductViewModel = viewModel(factory = factory)
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = secondaryContainerLight
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
                ProductScreen(navController = navController, viewModel = viewModel)
            }
            composable(Screens.Checkout.route) {
                // Checkout screen composable
                CheckoutScreen(navController = navController, viewModel = viewModel)
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
