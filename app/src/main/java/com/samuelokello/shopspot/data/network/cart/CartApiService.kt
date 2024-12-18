package com.samuelokello.shopspot.data.network.cart

import com.google.gson.annotations.SerializedName
import com.samuelokello.shopspot.data.network.cart.dto.UserCartResponseDto
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
data class AddCartRequestDto(
    val userId: Int,
    val date: String,
    val products: List<com.samuelokello.shopspot.data.network.cart.dto.CartProductDto>
)

data class UpdateCartRequestDto(
    val userId: Int,
    val date: String,
    val products: List<com.samuelokello.shopspot.data.network.cart.dto.CartProductDto>
)

data class CartProductDto(
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int
)

// Response DTOs (you already have these)
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