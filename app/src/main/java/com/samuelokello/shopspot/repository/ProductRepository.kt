package com.samuelokello.shopspot.repository

import com.samuelokello.shopspot.data.Product
import com.samuelokello.shopspot.data.network.NetworkManager

class ProductRepository {
    suspend fun getProducts(): List<Product> {
        return NetworkManager.fetchProducts()
    }
}