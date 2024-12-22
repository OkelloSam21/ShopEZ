package com.samuelokello.shopspot.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samuelokello.shopspot.ShopSpotApplication
import com.samuelokello.shopspot.ui.cart.CartViewModel
import com.samuelokello.shopspot.ui.home.HomeViewModel
import com.samuelokello.shopspot.ui.productdetails.ProductDetailViewModel
import com.samuelokello.shopspot.ui.search.SearchViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                repository = shopSpotApplication().container.productRepository
            )
        }
        initializer {
            SearchViewModel(
                repository = shopSpotApplication().container.productRepository
            )
        }
        initializer {
            ProductDetailViewModel(
                repository = shopSpotApplication().container.productRepository,
                cartRepository = shopSpotApplication().container.cartRepository
            )
        }
        initializer {
            CartViewModel(
                cartRepository = shopSpotApplication().container.cartRepository,
                productRepository = shopSpotApplication().container.productRepository
            )
        }
    }
}

/**
 * Extension function to queries for Application object and returns an instance of
 *  [ShopSpotApplication].
 */
fun CreationExtras.shopSpotApplication(): ShopSpotApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ShopSpotApplication)