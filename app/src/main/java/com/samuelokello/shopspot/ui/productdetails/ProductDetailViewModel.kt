package com.samuelokello.shopspot.ui.productdetails

import android.util.Log
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
                ).collect { result ->
                    result.fold(
                        onSuccess = { cart ->
                            // Handle successful cart addition
                            // You might want to show a success message or update UI
                            Log.e("ProductViewmode", "item aded succesfully")
                        },
                        onFailure = { exception ->
                            _state.value = ProductDetailUiState.Error(
                                message = exception.message ?: "Failed to add item to cart"
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                _state.value = ProductDetailUiState.Error(
                    message = e.message ?: "An error occurred"
                )
            }
        }
    }
}

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Success(val product: Product) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}