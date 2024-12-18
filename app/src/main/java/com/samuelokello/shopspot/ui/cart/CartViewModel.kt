package  com.samuelokello.shopspot.ui.cart


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.data.repository.CartRepository
import com.samuelokello.shopspot.domain.UserCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    init {
        fetchCartItems()
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            cartRepository.getUserCarts(USER_ID) // Replace USER_ID with actual user ID
                .collect { result ->
                    result.fold(
                        onSuccess = { carts ->
                            val latestCart = carts.maxByOrNull { it.date }
                            if (latestCart != null) {
                                loadCartWithProducts(latestCart)
                            } else {
                                _cartItems.value = emptyList()
                                _uiState.value = CartUiState.Success(emptyList())
                            }
                        },
                        onFailure = { exception ->
                            _uiState.value = CartUiState.Error("Failed to load cart: ${exception.message}")
                        }
                    )
                }
        }
    }

    private fun loadCartWithProducts(cart: UserCart) {
        try {
            val cartItems = cart.products.map { cartProduct ->
                val product = productRepository.getProductById(cartProduct.productId)
                CartItem(product, cartProduct.quantity)
            }
            _cartItems.value = cartItems
            calculateTotal()
            _uiState.value = CartUiState.Success(cartItems)
        } catch (e: Exception) {
            _uiState.value = CartUiState.Error("Failed to load product details: ${e.message}")
        }
    }

    fun updateQuantity(productId: Int, increase: Boolean) {
        viewModelScope.launch {
            val item = _cartItems.value.find { it.product.id == productId } ?: return@launch
            val newQuantity = if (increase) item.quantity + 1 else (item.quantity - 1).coerceAtLeast(1)

            cartRepository.updateItemQuantity(USER_ID, productId, newQuantity)
                .collect { result ->
                    result.fold(
                        onSuccess = { updatedCart ->
                            // Refresh the entire cart to ensure consistency
                            loadCartWithProducts(updatedCart)
                        },
                        onFailure = { exception ->
                            _uiState.value = CartUiState.Error("Failed to update quantity: ${exception.message}")
                        }
                    )
                }
        }
    }

    fun removeItem(productId: Int) {
        viewModelScope.launch {
            cartRepository.removeItemFromCart(USER_ID, productId)
                .collect { result ->
                    result.fold(
                        onSuccess = { updatedCart ->
                            // Refresh the entire cart to ensure consistency
                            loadCartWithProducts(updatedCart)
                        },
                        onFailure = { exception ->
                            _uiState.value = CartUiState.Error("Failed to remove item: ${exception.message}")
                        }
                    )
                }
        }
    }

    private fun calculateTotal() {
        _totalPrice.value = _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun refreshCart() {
        viewModelScope.launch {
            cartRepository.refreshCarts(USER_ID)
                .collect { result ->
                    result.fold(
                        onSuccess = { carts ->
                            val latestCart = carts.maxByOrNull { it.date }
                            if (latestCart != null) {
                                loadCartWithProducts(latestCart)
                            }
                        },
                        onFailure = { exception ->
                            _uiState.value = CartUiState.Error("Failed to refresh cart: ${exception.message}")
                        }
                    )
                }
        }
    }

    fun addToCart(productId: Int, quantity: Int = 1) {
        viewModelScope.launch {
            cartRepository.addItemToCart(USER_ID, productId, quantity)
                .collect { result ->
                    result.fold(
                        onSuccess = { updatedCart ->
                            loadCartWithProducts(updatedCart)
                        },
                        onFailure = { exception ->
                            _uiState.value = CartUiState.Error("Failed to add item to cart: ${exception.message}")
                        }
                    )
                }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart(USER_ID)
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            _cartItems.value = emptyList()
                            calculateTotal()
                            _uiState.value = CartUiState.Success(emptyList())
                        },
                        onFailure = { exception ->
                            _uiState.value = CartUiState.Error("Failed to clear cart: ${exception.message}")
                        }
                    )
                }
        }
    }

    companion object {
        private const val USER_ID = 1 // Replace with actual user ID from authentication
    }

}
sealed interface CartUiState {
    data object Loading : CartUiState
    data class Success(val items: List<CartItem>) : CartUiState
    data class Error(val message: String) : CartUiState
}