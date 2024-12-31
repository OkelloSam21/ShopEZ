package com.samuelokello.shopspot.ui.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelokello.shopspot.R
import com.samuelokello.shopspot.domain.Product
import com.samuelokello.shopspot.ui.AppViewModelProvider
import com.samuelokello.shopspot.ui.components.ErrorView

/**
 * @Product Screen - Lists all available products
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToItemDetails: (productId: Int) -> Unit,
    onBackPressed: () -> Unit
) {
    val state by viewModel.homeUiState.collectAsState()

    var backPressedTime by remember { mutableStateOf(0L) }
    val context = LocalContext.current

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime > 2000) {
            backPressedTime = currentTime
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        } else {
            onBackPressed()
        }
    }

    when (state) {

        is HomeUiState.Error -> ErrorView(
            message = (state as HomeUiState.Error).message,
            onRetry = { viewModel.loadProducts()}
        )

        is HomeUiState.Loading -> LoadingScreen()

        is HomeUiState.Success ->
            ProductList(
                products = (state as HomeUiState.Success).products,
                navigateToItemDetails = { navigateToItemDetails(it) },
            )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>,
    navigateToItemDetails: (productId: Int) -> Unit
) {
    Column {

      LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = modifier.padding()
        ) {

            items(products) { product ->
                ProductItem(
                    product,
                    navigateToItemDetails = { navigateToItemDetails(product.id) }
                )
            }

        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading),
            contentScale = ContentScale.Crop
        )
    }

}


