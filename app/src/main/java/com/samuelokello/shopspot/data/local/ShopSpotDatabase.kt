package com.samuelokello.shopspot.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samuelokello.shopspot.data.local.cart.CartProductConverter
import com.samuelokello.shopspot.data.local.cart.UserCartDao
import com.samuelokello.shopspot.data.local.cart.UserCartEntity
import com.samuelokello.shopspot.data.local.product.ProductDao
import com.samuelokello.shopspot.data.local.product.ProductEntity

@Database(entities = [ProductEntity::class,User::class, UserCartEntity::class], version = 1, exportSchema = false)
@TypeConverters(CartProductConverter::class)
abstract class ShopSpotDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): UserCartDao

    companion object {
        @Volatile
        private var Instance : ShopSpotDatabase? = null

        fun getDatabase(context: Context): ShopSpotDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context,ShopSpotDatabase::class.java, "shop_spot_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}