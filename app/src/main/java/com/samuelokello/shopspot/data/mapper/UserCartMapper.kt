package com.samuelokello.shopspot.data.mapper

import com.samuelokello.shopspot.data.local.cart.CartProductEntity
import com.samuelokello.shopspot.data.local.cart.UserCartEntity
import com.samuelokello.shopspot.data.network.cart.dto.CartProductDto
import com.samuelokello.shopspot.data.network.cart.dto.UserCartResponseDto
import com.samuelokello.shopspot.domain.CartProduct
import com.samuelokello.shopspot.domain.UserCart

// DTO to Domain
fun UserCartResponseDto.toDomain() = UserCart(
    id = id,
    date = date,
    products = cartProductDto.map { it.toDomain() },
    userId = userId,
    v = v
)

fun CartProductDto.toDomain() = CartProduct(
    productId = productId,
    quantity = quantity
)

// Domain to Entity
fun UserCart.toEntity() = UserCartEntity(
    id = id,
    date = date,
    products = products.map { it.toEntity() },
    userId = userId,
    v = v
)

fun CartProduct.toEntity() = CartProductEntity(
    productId = productId,
    quantity = quantity
)

// Entity to Domain
fun UserCartEntity.toDomain() = UserCart(
    id = id,
    date = date,
    products = products.map { it.toDomain() },
    userId = userId,
    v = v
)

fun CartProductEntity.toDomain() = CartProduct(
    productId = productId,
    quantity = quantity
)

//fun UserCart.toDto() = UserCartRequestDto(
//    userId = userId,
//    products = products.map { it.toDto() }
//)
//
//fun CartProduct.toDto() = CartProductRequestDto(
//    productId = productId,
//    quantity = quantity
//)