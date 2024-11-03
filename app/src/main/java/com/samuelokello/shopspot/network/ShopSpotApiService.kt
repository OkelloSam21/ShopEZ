package com.samuelokello.shopspot.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samuelokello.shopspot.data.Product
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://fakestoreapi.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ShopSpotApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}