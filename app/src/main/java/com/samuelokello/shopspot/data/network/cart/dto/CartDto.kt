package com.samuelokello.shopspot.data.network.cart.dto

data class CartDto(
    val productId: Int,
    val quantity: Int,
    val date: String,
    val userId: Int,
    val v: Int
)