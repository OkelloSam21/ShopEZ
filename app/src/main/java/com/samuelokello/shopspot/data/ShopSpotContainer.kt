package com.samuelokello.shopspot.data

import android.content.Context
import androidx.annotation.UiThread
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samuelokello.shopspot.data.local.product.ProductDao
import com.samuelokello.shopspot.data.local.ShopSpotDatabase
import com.samuelokello.shopspot.data.local.auth.AuthPreferences
import com.samuelokello.shopspot.data.local.auth.Constants.AUTH_PREFERENCES
import com.samuelokello.shopspot.data.local.cart.UserCartDao
import com.samuelokello.shopspot.data.mapper.ProductApiMapper
import com.samuelokello.shopspot.data.mapper.ProductEntityMapper
import com.samuelokello.shopspot.data.network.auth.AuthApiService
import com.samuelokello.shopspot.data.network.cart.CartApiService
import com.samuelokello.shopspot.data.network.product.ProductApiService
import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.data.repository.ProductRepositoryImpl
import com.samuelokello.shopspot.data.repository.CartRepository
import com.samuelokello.shopspot.data.repository.CartRepositoryImpl
import com.samuelokello.shopspot.data.repository.LoginRepositoryImpl
import com.samuelokello.shopspot.domain.repository.LoginRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ShopSpotContainer {
    val productRepository: ProductRepository
    val cartRepository: CartRepository
    val loginRepository: LoginRepository
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_PREFERENCES)

class DefaultAppContainer(private val context: Context) : ShopSpotContainer {
    private val baseUrl = "https://fakestoreapi.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    // Network
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    // API Services
    private val productApiService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    private val cartApiService: CartApiService by lazy {
        retrofit.create(CartApiService::class.java)
    }

    private val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    // Database
    private val database: ShopSpotDatabase by lazy {
        ShopSpotDatabase.getDatabase(context)
    }

    // DAOs
    private val productDao: ProductDao by lazy {
        database.productDao()
    }

    private val cartDao: UserCartDao by lazy {
        database.cartDao()
    }

    private val authPreferences: AuthPreferences by lazy {
        AuthPreferences(context.dataStore)
    }

    // Mappers
    private val productApiMapper: ProductApiMapper by lazy {
        ProductApiMapper()
    }

    private val productEntityMapper: ProductEntityMapper by lazy {
        ProductEntityMapper()
    }

    // Repositories
    override val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(
            productApiService = productApiService,
            productDao = productDao,
            productEntityMapper = productEntityMapper,
            productApiMapper = productApiMapper
        )
    }

    override val cartRepository: CartRepository by lazy {
        CartRepositoryImpl(
            api = cartApiService,
            dao = cartDao
        )
    }

    override val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl(
            authPreferences = authPreferences,
            authApiService = authApiService
        )
    }

    companion object {
        @Volatile
        private var Instance: ShopSpotContainer? = null

        fun getInstance(context: Context): ShopSpotContainer {
            return Instance ?: synchronized(this) {
                Instance ?: DefaultAppContainer(context).also { Instance = it }
            }
        }
    }
}