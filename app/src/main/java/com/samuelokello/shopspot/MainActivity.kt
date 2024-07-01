package com.samuelokello.shopspot

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.samuelokello.shopspot.ui.home.ProductScreen
import com.samuelokello.shopspot.ui.home.ProductViewModel
import com.samuelokello.shopspot.ui.theme.ShopSpotTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopSpotTheme {
                ProductScreen(viewModel = viewModel)
            }
        }
    }
}
