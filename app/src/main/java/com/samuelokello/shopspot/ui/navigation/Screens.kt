package com.samuelokello.shopspot.ui.navigation

sealed class Screens (val route : String){
    object Products : Screens("products_route")
    object Checkout : Screens("checkout_screen")
    object OrderPlaced: Screens("order_placed_screen")
}