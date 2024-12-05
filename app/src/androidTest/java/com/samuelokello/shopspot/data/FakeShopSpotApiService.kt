package com.samuelokello.shopspot.data

import com.samuelokello.shopspot.data.network.ProductApiModel
import com.samuelokello.shopspot.data.network.ShopSpotApiService

class FakeShopSpotApiService: ShopSpotApiService {
    override suspend fun getProducts(): List<ProductApiModel> {
       return FakeDataSource.productApiList
    }

    override suspend fun getCategories(): List<String> {
        TODO("Not yet implemented")
    }

}