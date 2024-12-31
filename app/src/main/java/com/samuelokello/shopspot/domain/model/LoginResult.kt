package com.samuelokello.shopspot.domain.model

data class LoginResult(
    val passwordError: String? = null,
    val usernameError: String? = null,
//    val result: Resource<Unit>? = null
)