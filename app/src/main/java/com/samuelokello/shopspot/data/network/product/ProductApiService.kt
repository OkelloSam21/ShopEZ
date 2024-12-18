package com.samuelokello.shopspot.data.network.product

import com.samuelokello.shopspot.data.network.product.dto.ProductDto
import retrofit2.http.GET


interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
    @GET("Category")
    suspend fun getCategories(): List<String>
}