package com.samuelokello.shopspot.data.network

import kotlinx.serialization.Serializable

@Serializable
data class ProductApiModel(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingApiModel?
)

@Serializable
data class RatingApiModel(
    val rate: Double,
    val count: Int
)