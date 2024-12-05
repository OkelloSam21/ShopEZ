package com.samuelokello.shopspot.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
    private lateinit var productDao: ProductDao
    private lateinit var shopSpotDatabase: SHopSpotDatabase

    private var product1 = ProductEntity(1,"title1",3000.00,"some desc","category","img",4.0,344)
    private var product2 = ProductEntity(2,"title2",4500.00,"some desc","category","img",3.7,34)

    @Before
    fun createDB() {
        val context: Context = ApplicationProvider.getApplicationContext()
        shopSpotDatabase = Room.inMemoryDatabaseBuilder(context, SHopSpotDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        productDao = shopSpotDatabase.productDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        shopSpotDatabase.close()
    }

    @Test
    @Throws(IOException::class)
    fun productDaoInsert_insertProductsToDB() = runBlocking{
        addItemsToDB()

        val allProducts = productDao.getProducts().first()

        assertEquals(allProducts[0],product1)

    }

    @Test
    @Throws(IOException::class)
    fun productDaoGetProducts_returnsAllProductsFromDB() = runBlocking{
        addItemsToDB()
        val allProducts = productDao.getProducts().first()

        assertEquals(allProducts[0],product1)
        assertEquals(allProducts[1],product2)
    }

    @Test
    fun productDaoGetProducts_returnsEmptyListWhenNoProductsInserted() = runBlocking {
        val allProducts = productDao.getProducts().first()
        assertTrue(allProducts.isEmpty())
    }


    private suspend fun addItemsToDB() {
        productDao.insertProducts(listOf( product1,product2))
    }
}