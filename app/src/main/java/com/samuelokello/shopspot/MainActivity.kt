package com.samuelokello.shopspot

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samuelokello.shopspot.repository.ProductRepository
import com.samuelokello.shopspot.ui.checkout.CheckoutScreen
import com.samuelokello.shopspot.ui.navigation.Screens
import com.samuelokello.shopspot.ui.navigation.bottom_navigation.BottomNavigationBar
import com.samuelokello.shopspot.ui.products.ProductScreen
import com.samuelokello.shopspot.ui.products.ProductViewModel
import com.samuelokello.shopspot.ui.products.ProductViewModelFactory
import com.samuelokello.shopspot.ui.theme.ShopSpotTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            ShopSpotTheme {
                BottomNavigationBar()
            }
        }
    }
}
