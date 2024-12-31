package com.samuelokello.shopspot.data.network.cart.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCartResponseDto(
    @SerialName("date")
    val date: String,
    @SerialName("id")
    val id: Int,
    @SerialName("products")
    val cartProductDto: List<CartProductDto>,
    @SerialName("userId")
    val userId: Int,
    @SerialName("__v")
    val v: Int? = null
)