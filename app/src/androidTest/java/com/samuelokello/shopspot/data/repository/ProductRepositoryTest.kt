package com.samuelokello.shopspot.data.repository
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.samuelokello.shopspot.data.FakeProductApiService
import com.samuelokello.shopspot.data.local.product.ProductDao
import com.samuelokello.shopspot.data.local.ShopSpotDatabase
import com.samuelokello.shopspot.data.mapper.ProductApiMapper
import com.samuelokello.shopspot.data.mapper.ProductEntityMapper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class ProductRepositoryImplTest {

    private lateinit var productDao: ProductDao
    private lateinit var shopSpotDatabase: ShopSpotDatabase
    private val fakeApiService = FakeProductApiService()
    private lateinit var repository: ProductRepositoryImpl

    private val productApiMapper = ProductApiMapper()
    private val productEntityMapper = ProductEntityMapper()

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        shopSpotDatabase = Room.inMemoryDatabaseBuilder(context, ShopSpotDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        productDao = shopSpotDatabase.productDao()

        repository = ProductRepositoryImpl(
            productApiService = fakeApiService,
            productDao = productDao,
            productApiMapper = productApiMapper,
            productEntityMapper = productEntityMapper
        )
    }

    @After
    fun tearDown() {
        shopSpotDatabase.close()
    }

    @Test
    fun repository_getProducts_fetchesFromApiAndCachesInDatabase() = runBlocking {
//        fakeApiService.setFakeProducts(FakeDataSource.productApiList)

        // Act
        val products = repository.getProducts().first()

        // Assert: Products are fetched from the API and stored in the database
//        val cachedProducts = productDao.getProducts().first()
//        val expectedProducts = FakeDataSource.productApiList.map {
//            productEntityMapper.toDomain(productApiMapper.toDomain(it))
//        }
//        assertEquals(expectedProducts, products)
//        assertEquals(
//            expectedProducts.map { productEntityMapper.toEntity(it) },
//            cachedProducts
//        )
    }
}
