package com.samuelokello.shopspot.data

interface ShopSpotRepository {
    suspend fun getProducts(): List<Product>
}

