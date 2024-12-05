package com.samuelokello.shopspot.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samuelokello.shopspot.ShopSpotApplication
import com.samuelokello.shopspot.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                repository = shopSpotApplication().container.productRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 *  [ShopSpotApplication].
 */
fun CreationExtras.shopSpotApplication(): ShopSpotApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ShopSpotApplication)