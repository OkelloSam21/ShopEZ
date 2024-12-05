package com.samuelokello.shopspot.data.network

import com.samuelokello.shopspot.domain.Product
import retrofit2.http.GET


interface ShopSpotApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductApiModel>
    @GET("Category")
    suspend fun getCategories(): List<String>
}