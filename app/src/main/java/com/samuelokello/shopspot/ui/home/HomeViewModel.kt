package com.samuelokello.shopspot.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelokello.shopspot.data.repository.ProductRepository
import com.samuelokello.shopspot.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * ViewModel
 */
class HomeViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    var homeUiState: StateFlow<HomeUiState> =_homeUiState.asStateFlow()


    fun loadProducts() {
        viewModelScope.launch {
            repository.getProducts()
                .onStart {
                    _homeUiState.value = HomeUiState.Loading
                }
                .catch {
                    _homeUiState.value = HomeUiState.Error
                }
                .collect { products ->
                    _homeUiState.value = HomeUiState.Success(products)
                }
        }
    }

    init {
        loadProducts()
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class Success(val products:List<Product>) :HomeUiState
}