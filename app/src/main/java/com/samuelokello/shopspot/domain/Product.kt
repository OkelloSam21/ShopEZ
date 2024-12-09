package com.samuelokello.shopspot.domain

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Double,
    val count: Int,
    val isFavourite: Boolean? = false
)
