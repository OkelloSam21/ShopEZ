package com.samuelokello.shopspot.data.network.cart.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartProductDto(
    @SerialName("productId")
    val productId: Int,
    @SerialName("quantity")
    val quantity: Int,
)