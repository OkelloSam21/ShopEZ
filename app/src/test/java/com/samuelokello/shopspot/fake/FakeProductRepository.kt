package com.samuelokello.shopspot.fake

import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.domain.Product
import kotlinx.coroutines.flow.Flow

class FakeProductRepository: ProductRepository {
    override fun getProducts(): Flow<List<Product>> {
      return FakeDataSource.fakeProductsList
    }
}