package com.samuelokello.shopspot.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.data.repository.CartRepository
import com.samuelokello.shopspot.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val cartRepository: CartRepository
): ViewModel () {
    private val _state = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val state = _state.asStateFlow()

    fun getProductById(productId: Int) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getProductById(id = productId)
                }
                _state.value = ProductDetailUiState.Success(product = result)
            } catch (e: Exception) {
                _state.value = ProductDetailUiState.Error(message = e.message ?: "An error occurred")
            }
        }
    }


    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                cartRepository.addItemToCart(
                    userId = 1,
                    productId = product.id,
                    quantity = 1

                )
            }catch (e: Exception){

            }
        }
    }
}

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Success(val product: Product) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}