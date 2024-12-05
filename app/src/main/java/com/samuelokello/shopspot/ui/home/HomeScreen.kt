package com.samuelokello.shopspot.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.samuelokello.shopspot.R
import com.samuelokello.shopspot.domain.Product

/**
 * @Product Screen - Lists all available products
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToItemDetails: (product: Product) -> Unit,
    modifier: Modifier = Modifier,
//    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val state by viewModel.homeUiState.collectAsState()

    when (state) {
        HomeUiState.Error -> ErrorScreen()
        HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Success ->
            ProductList(
                products = (state as HomeUiState.Success).products,
                navigateToItemDetails = { navigateToItemDetails(it) },
//                modifier = modifier.padding(top = contentPadding.calculateTopPadding())
            )

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>,
    navigateToItemDetails: (product: Product) -> Unit
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
                    navigateToItemDetails = { navigateToItemDetails(product) }
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

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

