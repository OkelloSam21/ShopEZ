package com.samuelokello.shopspot.data.repository

import com.samuelokello.shopspot.data.local.auth.AuthPreferences
import com.samuelokello.shopspot.data.network.auth.AuthApiService
import com.samuelokello.shopspot.data.network.auth.dto.UserResponseDto
import com.samuelokello.shopspot.data.network.auth.request.LoginRequest
import com.samuelokello.shopspot.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class LoginRepositoryImpl (
    private val authApiService: AuthApiService,
    private val authPreferences: AuthPreferences
) : LoginRepository {

    override suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean) {

        try {
            val response = authApiService.loginUser(loginRequest)

            // Save user data if available
            getAllUsers(loginRequest.username)?.let { userData ->
                authPreferences.saveUserdata(userData)
            }

            if (rememberMe) {
                authPreferences.saveAccessToken(response.token)
            }
        } catch (e: IOException) {
            throw IOException("Could not reach the server, please check your internet connection!")
        } catch (e: HttpException) {
            throw e.response()?.let { HttpException(it) }!!
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun autoLogin() {
        val accessToken = authPreferences.getAccessToken.first()

        if (accessToken.isEmpty()) {
            throw IllegalStateException("No access token found")
        }
    }

    override suspend fun logout() {
        try {
            authPreferences.clearAccessToken()
            val remainingToken = authPreferences.getAccessToken.first()

            if (remainingToken.isNotEmpty()) {
                throw IllegalStateException("Failed to clear access token")
            }

            authPreferences.clearAccessToken()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getAllUsers(email: String): UserResponseDto? {
        return try {
            val users = authApiService.getAllUsers()
            users.find { it.email == email }
        } catch (e: Exception) {
            null
        }
    }
}