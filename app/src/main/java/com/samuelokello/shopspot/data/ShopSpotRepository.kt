package com.samuelokello.shopspot.data

import com.samuelokello.shopspot.network.ShopSpotApiService

interface ShopSpotRepository {
    suspend fun getProducts(): List<Product>
}

