package com.samuelokello.shopspot.data.local.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "products", indices = [Index(value= ["title","category"])])
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    @ColumnInfo(name = "image_url")val image: String,
    val rate: Double,
    @ColumnInfo(name = "rating_count")val count: Int
)

