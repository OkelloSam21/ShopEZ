package com.samuelokello.shopspot.data.network

import android.util.Log
import com.samuelokello.shopspot.data.Product
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NetworkManager {
    private const val BASE_URL = "https://fakestoreapi.com/"

    suspend fun fetchProducts(): List<Product> = withContext(Dispatchers.IO) {
        val url = URL("${BASE_URL}products")
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use(BufferedReader::readText)
                parseProductsJson(response)
            } else {
                Log.e("NetworkManager", "HTTP error code: $responseCode")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("NetworkManager", "Error fetching products", e)
            emptyList()
        } finally {
            connection.disconnect()
        }
    }

    private fun parseProductsJson(jsonString: String): List<Product> {
        val productList = mutableListOf<Product>()
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val product = Product(
                id = jsonObject.getInt("id"),
                title = jsonObject.getString("title"),
                price = jsonObject.getDouble("price"),
                description = jsonObject.getString("description"),
                category = jsonObject.getString("category"),
                image = jsonObject.getString("image")
            )
            productList.add(product)
        }
        return productList
    }
}