package com.samuelokello.shopspot.ui.checkout

import androidx.lifecycle.ViewModel

class CheckoutViewModel: ViewModel() {

}

sealed interface CheckoutUiState {
    object Product: CheckoutUiState
    object Loading: CheckoutUiState
    data class Error(val error: String): CheckoutUiState
}