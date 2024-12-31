package com.samuelokello.shopspot.domain.repository

import com.samuelokello.shopspot.data.network.auth.request.LoginRequest

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean)
    suspend fun autoLogin()
    suspend fun logout()
}