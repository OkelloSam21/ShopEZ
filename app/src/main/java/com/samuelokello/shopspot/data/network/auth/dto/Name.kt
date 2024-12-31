package com.samuelokello.shopspot.data.network.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Name(
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String
)