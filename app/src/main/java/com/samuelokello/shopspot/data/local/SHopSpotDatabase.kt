package com.samuelokello.shopspot.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class,User::class], version = 1, exportSchema = false)
abstract class SHopSpotDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var Instance : SHopSpotDatabase? = null

        fun getDatabase(context: Context): SHopSpotDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context,SHopSpotDatabase::class.java, "shop_spot_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}