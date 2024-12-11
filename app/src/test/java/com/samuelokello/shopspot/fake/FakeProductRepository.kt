package com.samuelokello.shopspot.fake

import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.domain.Product
import kotlinx.coroutines.flow.Flow

class FakeProductRepository: ProductRepository {
    override fun getProducts(): Flow<List<Product>> = FakeDataSource.fakeProductsList


    override fun searchProductsWithFilters(
        query: String,
        minPrice: Double?,
        maxPrice: Double?,
        category: String?,
        minCount: Int?,
        minRating: Double?
    ): Flow<List<Product>> = FakeDataSource.fakeProductsList

    override fun getProductById(id: Int): Product {
        TODO("Not yet implemented")
    }

}