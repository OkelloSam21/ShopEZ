package com.samuelokello.shopspot.data.network.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    @SerialName("address")
    val address: Address,
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: Name,
    @SerialName("password")
    val password: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("username")
    val username: String,
    @SerialName("__v")
    val v: Int
)