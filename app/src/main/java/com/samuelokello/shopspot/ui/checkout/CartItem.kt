package com.samuelokello.shopspot.ui.checkout

import com.samuelokello.shopspot.domain.Product

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)