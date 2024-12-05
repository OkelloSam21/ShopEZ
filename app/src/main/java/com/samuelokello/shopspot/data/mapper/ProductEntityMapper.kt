package com.samuelokello.shopspot.data.mapper

import com.samuelokello.shopspot.data.local.ProductEntity
import com.samuelokello.shopspot.domain.Product

class ProductEntityMapper {
    fun toDomain(entity: ProductEntity): Product {
        return Product(
            id = entity.id,
            title = entity.title,
            price = entity.price,
            description = entity.description,
            category = entity.category,
            image = entity.image,
            rating = entity.rate,
            count = entity.count
        )
    }

    fun toEntity(domain: Product): ProductEntity {
        return ProductEntity(
            id = domain.id,
            title = domain.title,
            price = domain.price,
            description = domain.description,
            category = domain.category,
            image = domain.image,
            rate = domain.rating,
            count = domain.count
        )
    }
}

