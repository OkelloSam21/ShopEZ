package com.samuelokello.shopspot.data.network.cart

import com.samuelokello.shopspot.data.network.cart.dto.CartProductDto
import com.samuelokello.shopspot.data.network.cart.dto.UserCartResponseDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApiService {
    @GET("carts/user/{userId}")
    suspend fun getUserCarts(
        @Path("userId") userId: Int,
        @Query("startdate") startDate: String? = null,
        @Query("enddate") endDate: String? = null
    ): List<UserCartResponseDto>

    @POST("carts")
    suspend fun addToCart(
        @Body cart: AddCartRequestDto
    ): UserCartResponseDto

    @PUT("carts/{cartId}")
    suspend fun updateCart(
        @Path("cartId") cartId: Int,
        @Body cart: UpdateCartRequestDto
    ): UserCartResponseDto

    @DELETE("carts/{cartId}")
    suspend fun deleteCart(
        @Path("cartId") cartId: Int
    ): UserCartResponseDto
}

// Request DTOs
@Serializable
data class AddCartRequestDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("date")
    val date: String,
    @SerialName("products")
    val products: List<CartProductDto>
)

@Serializable
data class UpdateCartRequestDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("date")
    val date: String,
    @SerialName("products")
    val products: List<CartProductDto>
)
