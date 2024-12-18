package com.samuelokello.shopspot.data.network.cart.dto

import com.google.gson.annotations.SerializedName

data class CartProductDto(
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int
)