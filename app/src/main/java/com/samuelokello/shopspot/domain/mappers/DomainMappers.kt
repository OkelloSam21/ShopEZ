package com.samuelokello.shopspot.domain.mappers

import com.samuelokello.shopspot.data.local.ProductEntity
import com.samuelokello.shopspot.data.network.ProductApiModel
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

fun ProductApiModel.toEntity(): ProductEntity {
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
