package com.samuelokello.shopspot.ui.cart

import com.samuelokello.shopspot.domain.Product

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)