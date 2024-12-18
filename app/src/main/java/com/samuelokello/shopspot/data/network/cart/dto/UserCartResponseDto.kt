package com.samuelokello.shopspot.data.network.cart.dto

import com.google.gson.annotations.SerializedName

data class UserCartResponseDto(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val cartProductDto: List<CartProductDto>,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("__v")
    val v: Int
)