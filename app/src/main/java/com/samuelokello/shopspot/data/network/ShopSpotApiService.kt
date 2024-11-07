package com.samuelokello.shopspot.data.network

import com.samuelokello.shopspot.data.Product
import retrofit2.http.GET


interface ShopSpotApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
    @GET("Category")
    suspend fun getCategories(): List<String>
}