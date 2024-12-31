package com.samuelokello.shopspot.domain.model

import com.samuelokello.shopspot.data.network.auth.dto.Address
import com.samuelokello.shopspot.data.network.auth.dto.Name

data class User(
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String,
)