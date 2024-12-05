package com.samuelokello.shopspot.data.mapper

import com.samuelokello.shopspot.data.local.ProductEntity
import com.samuelokello.shopspot.data.network.ProductApiModel
import com.samuelokello.shopspot.domain.Product

class ProductApiMapper {
    fun toEntity(apiModel: ProductApiModel): ProductEntity {
        return ProductEntity(
            id = apiModel.id,
            title = apiModel.title,
            price = apiModel.price,
            description = apiModel.description,
            category = apiModel.category,
            image = apiModel.image,
            rate = apiModel.rating?.rate ?: 0.00,
            count = apiModel.rating?.count ?: 0
        )
    }

    fun toDomain(apiModel: ProductApiModel): Product{
        return Product(
            id = apiModel.id,
            title = apiModel.title,
            price = apiModel.price,
            description = apiModel.description,
            category = apiModel.category,
            image = apiModel.image,
            rating = apiModel.rating?.rate ?: 0.00,
            count = apiModel.rating?.count ?: 0
        )
    }
}
