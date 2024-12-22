package com.samuelokello.shopspot.data.repository

import com.samuelokello.shopspot.data.local.cart.UserCartDao
import com.samuelokello.shopspot.data.mapper.toDomain
import com.samuelokello.shopspot.data.mapper.toEntity
import com.samuelokello.shopspot.data.network.cart.AddCartRequestDto
import com.samuelokello.shopspot.data.network.cart.CartApiService
import com.samuelokello.shopspot.data.network.cart.UpdateCartRequestDto
import com.samuelokello.shopspot.data.network.cart.dto.CartProductDto
import com.samuelokello.shopspot.domain.CartProduct
import com.samuelokello.shopspot.domain.UserCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface CartRepository {
    suspend fun getUserCarts(userId: Int): Flow<Result<List<UserCart>>>
    suspend fun addItemToCart(userId: Int, productId: Int, quantity: Int): Flow<Result<UserCart>>
    suspend fun removeItemFromCart(userId: Int, productId: Int): Flow<Result<UserCart>>
    suspend fun updateItemQuantity(userId: Int, productId: Int, quantity: Int): Flow<Result<UserCart>>
    suspend fun clearCart(userId: Int): Flow<Result<Unit>>
    suspend fun refreshCarts(userId: Int): Flow<Result<List<UserCart>>>
}

class CartRepositoryImpl (
    private val api: CartApiService,
    private val dao: UserCartDao
) : CartRepository {

    override suspend fun getUserCarts(userId: Int): Flow<Result<List<UserCart>>> = flow {
        try {

            val cachedCarts = dao.getCartsByUserId(userId).map { it.toDomain() }
            emit(Result.success(cachedCarts))

            val apiResponse = api.getUserCarts(userId)
            val carts = apiResponse.map { it.toDomain() }

            dao.clearAndInsertCarts(carts.map { it.toEntity() })

            emit(Result.success(carts))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addItemToCart(
        userId: Int,
        productId: Int,
        quantity: Int
    ): Flow<Result<UserCart>> = flow {
        try {
            // Get current cart or create product list
            val currentCart = dao.getCurrentCart(userId)?.toDomain()
            val currentProducts = currentCart?.products?.toMutableList() ?: mutableListOf()

            // Add new product or update quantity if exists
            val existingProductIndex = currentProducts.indexOfFirst { it.productId == productId }
            if (existingProductIndex != -1) {
                currentProducts[existingProductIndex] = currentProducts[existingProductIndex].copy(
                    quantity = currentProducts[existingProductIndex].quantity + quantity
                )
            } else {
                currentProducts.add(CartProduct(productId, quantity))
            }

            // Create request
            val request = AddCartRequestDto(
                userId = userId,
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                products = currentProducts.map { CartProductDto(it.productId, it.quantity) }
            )

            // Send to API
            val response = api.addToCart(request)
            val updatedCart = response.toDomain()

            // Update local cache
            dao.insertCart(updatedCart.toEntity())

            emit(Result.success(updatedCart))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun removeItemFromCart(
        userId: Int,
        productId: Int
    ): Flow<Result<UserCart>> = flow {
        try {
            // Get current cart
            val currentCart = dao.getCurrentCart(userId)?.toDomain()
                ?: throw IllegalStateException("No cart found for user $userId")

            // Remove product
            val updatedProducts = currentCart.products.filter { it.productId != productId }

            // Create request
            val request = UpdateCartRequestDto(
                userId = userId,
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                products = updatedProducts.map { CartProductDto(it.productId, it.quantity) }
            )

            // Update via API
            val response = api.updateCart(currentCart.id, request)
            val updatedCart = response.toDomain()

            // Update local cache
            dao.insertCart(updatedCart.toEntity())

            emit(Result.success(updatedCart))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateItemQuantity(
        userId: Int,
        productId: Int,
        quantity: Int
    ): Flow<Result<UserCart>> = flow {
        try {
            // Get current cart
            val currentCart = dao.getCurrentCart(userId)?.toDomain()
                ?: throw IllegalStateException("No cart found for user $userId")

            // Update product quantity
            val updatedProducts = currentCart.products.map { product ->
                if (product.productId == productId) {
                    product.copy(quantity = quantity)
                } else product
            }

            // Create request
            val request = UpdateCartRequestDto(
                userId = userId,
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                products = updatedProducts.map { CartProductDto(it.productId, it.quantity) }
            )

            // Update via API
            val response = api.updateCart(currentCart.id, request)
            val updatedCart = response.toDomain()

            // Update local cache
            dao.insertCart(updatedCart.toEntity())

            emit(Result.success(updatedCart))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun clearCart(userId: Int): Flow<Result<Unit>> = flow {
        try {
            // Get current cart
            val currentCart = dao.getCurrentCart(userId)?.toDomain()
                ?: throw IllegalStateException("No cart found for user $userId")

            // Delete via API
            api.deleteCart(currentCart.id)

            // Clear local cache
            dao.deleteCartsForUser(userId)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun refreshCarts(userId: Int): Flow<Result<List<UserCart>>> = flow {
        try {
            // Fetch fresh data from API
            val apiResponse = api.getUserCarts(userId)
            val carts = apiResponse.map { it.toDomain() }

            // Update cache
            dao.clearAndInsertCarts(carts.map { it.toEntity() })

            emit(Result.success(carts))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}

