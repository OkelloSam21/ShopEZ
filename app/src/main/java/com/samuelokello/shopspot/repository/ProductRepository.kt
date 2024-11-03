package com.samuelokello.shopspot.repository

import com.samuelokello.shopspot.data.Product
import com.samuelokello.shopspot.data.ShopSpotRepository
import com.samuelokello.shopspot.network.ShopSpotApiService

class ProductRepository  (
    private val shopSpotApiService: ShopSpotApiService
): ShopSpotRepository {
    override suspend fun getProducts(): List<Product> = shopSpotApiService.getProducts()
}