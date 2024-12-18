package com.samuelokello.shopspot.domain

data class Cart(
    val date: String,
    val id: Int,
    val cartProduct: List<CartProduct>,
    val userId: Int,
    val v: Int
)
