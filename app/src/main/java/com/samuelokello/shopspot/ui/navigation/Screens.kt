package com.samuelokello.shopspot.ui.navigation

import com.google.gson.Gson
import com.samuelokello.shopspot.data.Product

sealed class Screens (val route : String){
    data object Products : Screens("products_route")
    data object Checkout : Screens("checkout_screen")
    data object OrderPlaced: Screens("order_placed_screen")
    data object ProductDetailsScreen: Screens("product_details_screen?product={product}")

//    {
//        fun createRoute(product: String): String = "product_details_screen/$product"
//    }

    companion object {
        fun jsonToProduct(json: String): Product = Gson().fromJson(json, Product::class.java)
    }
}
