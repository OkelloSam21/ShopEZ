package com.samuelokello.shopspot.data

import com.samuelokello.shopspot.data.network.product.dto.ProductDto
import com.samuelokello.shopspot.data.network.product.ProductApiService

class FakeProductApiService: ProductApiService {
    override suspend fun getProducts(): List<ProductDto> {
       return FakeDataSource.productApiList
    }

    override suspend fun getCategories(): List<String> {
        TODO("Not yet implemented")
    }

}