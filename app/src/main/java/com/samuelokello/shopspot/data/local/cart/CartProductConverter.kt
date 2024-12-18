package com.samuelokello.shopspot.data.local.cart
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartProductConverter {
    @TypeConverter
    fun fromString(value: String): List<CartProductEntity> {
        val listType = object : TypeToken<List<CartProductEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<CartProductEntity>): String {
        return Gson().toJson(list)
    }
}