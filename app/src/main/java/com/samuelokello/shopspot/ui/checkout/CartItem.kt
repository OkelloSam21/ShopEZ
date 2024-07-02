package com.samuelokello.shopspot.ui.checkout

import com.samuelokello.shopspot.data.Product

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)