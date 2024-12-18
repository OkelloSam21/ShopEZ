package com.samuelokello.shopspot.data.local.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun  getProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE :id == id" )
    fun getProductById(id: Int): ProductEntity

    @Query("""
        SELECT * FROM products
        WHERE (:query IS NULL OR title LIKE '%' || :query || '%')
        AND (:minPrice IS NULL OR price >= :minPrice)
        AND (:maxPrice IS NULL OR price <= :maxPrice)
        AND (:category IS NULL OR category = :category)
        AND (:minRate IS NULL OR rate >= :minRate)
        AND (:minCount IS NULL OR rating_count >= :minCount)
    """)
        fun searchProductsWithFilters(
            query: String?,
            minPrice: Double?,
            maxPrice: Double?,
            category: String?,
            minRate: Double?,
            minCount: Int?
        ): Flow<List<ProductEntity>>

}