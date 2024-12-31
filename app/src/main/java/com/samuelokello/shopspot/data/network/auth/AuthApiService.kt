package com.samuelokello.shopspot.data.network.auth

import com.samuelokello.shopspot.data.network.auth.dto.UserResponseDto
import com.samuelokello.shopspot.data.network.auth.request.LoginRequest
import com.samuelokello.shopspot.data.network.auth.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/")
    suspend fun getAllUsers(): List<UserResponseDto>
}