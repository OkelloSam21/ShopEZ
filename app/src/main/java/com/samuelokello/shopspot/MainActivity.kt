package com.samuelokello.shopspot

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.samuelokello.shopspot.ui.navigation.bottom_navigation.BottomNavigationBar
import com.samuelokello.shopspot.ui.products.ProductScreen
import com.samuelokello.shopspot.ui.products.ProductViewModel
import com.samuelokello.shopspot.ui.theme.ShopSpotTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    private val viewModel: ProductViewModel by viewModels()

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
