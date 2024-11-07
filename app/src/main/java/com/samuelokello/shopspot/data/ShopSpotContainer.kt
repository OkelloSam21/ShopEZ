package com.samuelokello.shopspot.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samuelokello.shopspot.data.network.ShopSpotApiService
import com.samuelokello.shopspot.repository.ProductRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ShopSpotContainer {
    val productRepository: ProductRepository
}

class DefaultAppContainer : ShopSpotContainer {
    private val baseUrl = "https://fakestoreapi.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ShopSpotApiService by lazy {
        retrofit.create(ShopSpotApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        ProductRepository(retrofitService)
    }

}