package com.samuelokello.shopspot.ui.home

import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.domain.Product
import com.samuelokello.shopspot.fake.FakeDataSource
import com.samuelokello.shopspot.fake.FakeProductRepository
import com.samuelokello.shopspot.rule.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    @get:Rule
    val testDispatcher= TestDispatcherRule()


    @Test
    fun homeViewModel_loadProducts_verifyHomeUiStateSuccess() = runTest {
        val homeViewModel = HomeViewModel(repository = FakeProductRepository())

        homeViewModel.loadProducts()

        advanceUntilIdle()

        val expectedProducts = FakeDataSource.fakeProductsList.first()
        val expectedState = homeViewModel.homeUiState.value

        assertEquals(HomeUiState.Success(expectedProducts), expectedState)
    }

    @Test
    fun homeViewModel_loadProducts_verifyErrorState() = runTest {
        val errorRepository = object : ProductRepository {
            override fun getProducts(): Flow<List<Product>> = flow {
                throw Exception("Network Error")
            }
        }
        val homeViewModel = HomeViewModel(repository = errorRepository)

        homeViewModel.loadProducts()

        advanceUntilIdle()

        val expectedState = homeViewModel.homeUiState.value
        assertEquals(HomeUiState.Error, expectedState)
    }


}