package com.samuelokello.shopspot.domain

// UserCart.kt (Domain Model)
data class UserCart(
    val id: Int,
    val date: String,
    val products: List<CartProduct>,
    val userId: Int,
    val v: Int? = null
)

// CartProduct.kt (Domain Model)
data class CartProduct(
    val productId: Int,
    val quantity: Int
)