package com.samuelokello.shopspot.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samuelokello.shopspot.data.local.ProductDao
import com.samuelokello.shopspot.data.local.SHopSpotDatabase
import com.samuelokello.shopspot.data.mapper.ProductApiMapper
import com.samuelokello.shopspot.data.mapper.ProductEntityMapper
import com.samuelokello.shopspot.data.network.ShopSpotApiService
import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.data.repository.ProductRepositoryImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ShopSpotContainer {
    val productRepository: ProductRepository
}

class DefaultAppContainer(context: Context) : ShopSpotContainer {
    private val baseUrl = "https://fakestoreapi.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ShopSpotApiService by lazy {
        retrofit.create(ShopSpotApiService::class.java)
    }

    private val database: SHopSpotDatabase by lazy {
        SHopSpotDatabase.getDatabase(context)
    }

    private val productDao: ProductDao by lazy {
        database.productDao()
    }

    private val productApiMapper: ProductApiMapper by lazy {
        ProductApiMapper()
    }

    private val productEntityMapper: ProductEntityMapper by lazy {
        ProductEntityMapper()
    }

    override val productRepository: ProductRepositoryImpl by lazy {
        ProductRepositoryImpl(
            shopSpotApiService = retrofitService,
            productDao = productDao,
            productEntityMapper = productEntityMapper,
            productApiMapper = productApiMapper
        )
    }

}