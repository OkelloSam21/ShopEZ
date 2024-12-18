package com.samuelokello.shopspot.data.mapper

import com.samuelokello.shopspot.data.local.product.ProductEntity
import com.samuelokello.shopspot.data.network.product.dto.ProductDto
import com.samuelokello.shopspot.domain.Product

class ProductApiMapper {
    fun toEntity(apiModel: ProductDto): ProductEntity {
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

    fun toDomain(apiModel: ProductDto): Product{
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
