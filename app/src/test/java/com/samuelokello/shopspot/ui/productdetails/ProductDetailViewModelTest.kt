package com.samuelokello.shopspot.ui.productdetails

import com.samuelokello.shopspot.fake.FakeProductRepository
import com.samuelokello.shopspot.rule.TestDispatcherRule
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ProductDetailViewModelTest {

   @get:Rule
   val testDispatcher = TestDispatcherRule()

//    @Test
//    fun productDetailsViewModel_getProductById_verifyProductUiStateSuccess() = runTest {
//        val productDetailsViewModel = ProductDetailViewModel(repository = FakeProductRepository())
//
//        productDetailsViewModel.getProductById(productId =  1)
//
//        advanceUntilIdle()
//
//    }
}