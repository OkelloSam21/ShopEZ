package com.samuelokello.shopspot.data.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserCartDao {
    @Query("SELECT * FROM user_carts WHERE userId = :userId")
    suspend fun getCartsByUserId(userId: Int): List<UserCartEntity>

    @Query("SELECT * FROM user_carts WHERE userId = :userId ORDER BY date DESC LIMIT 1")
    suspend fun getCurrentCart(userId: Int): UserCartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: UserCartEntity)

    @Query("DELETE FROM user_carts WHERE userId = :userId")
    suspend fun deleteCartsForUser(userId: Int)

    @Transaction
    suspend fun clearAndInsertCarts(carts: List<UserCartEntity>) {
        deleteAllCarts()
        insertCarts(carts)
    }

    @Query("DELETE FROM user_carts")
    suspend fun deleteAllCarts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(carts: List<UserCartEntity>)
}