package com.samuelokello.shopspot.domain.mappers

import com.samuelokello.shopspot.data.local.product.ProductEntity
import com.samuelokello.shopspot.data.network.product.dto.ProductDto
import com.samuelokello.shopspot.domain.Product

fun ProductEntity.toDomainModel(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = rate,
        count = count
    )
}

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rate = rating?.rate ?: 0.00,
        count = rating?.count ?: 0
    )
}
