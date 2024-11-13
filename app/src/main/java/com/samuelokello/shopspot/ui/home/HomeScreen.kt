package com.samuelokello.shopspot.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.samuelokello.shopspot.R
import com.samuelokello.shopspot.data.Product

/**
 * @Product Screen - Lists all available products
 */
@Composable
fun HomeScreen(
    state: HomeUiState,
    viewModel: ProductViewModel,
    navigateToItemDetails: (product: Product) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadProducts()
    }

    when (state) {
        HomeUiState.Error -> ErrorScreen()
        HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Success ->
            ProductList(
                products = state.products,
                viewModel = viewModel,
                navigateToItemDetails = { navigateToItemDetails(it) },
                modifier = modifier.padding(top = contentPadding.calculateTopPadding())
            )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>,
    viewModel: ProductViewModel,
    navigateToItemDetails: (product: Product) -> Unit
) {
    Column {
        val onActiveChange = {}
        val colors1 = SearchBarDefaults.colors()
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    enabled = false,
                    placeholder = {
                        Text(text = "search")
                    },
                    leadingIcon = null,
                    trailingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "search")
                    },
                    interactionSource = null,
                )
            },
            expanded = false,
            onExpandedChange = { onActiveChange() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = SearchBarDefaults.inputFieldShape,
            colors = colors1,
            tonalElevation = SearchBarDefaults.TonalElevation,
            shadowElevation = SearchBarDefaults.ShadowElevation,
            windowInsets = SearchBarDefaults.windowInsets,
            content =  {
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = Modifier.padding()
        ) {
            items(products) { product ->
                ProductItem(
                    product,
                    productViewModel = viewModel,
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
            contentDescription = stringResource(R.string.loading)
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

