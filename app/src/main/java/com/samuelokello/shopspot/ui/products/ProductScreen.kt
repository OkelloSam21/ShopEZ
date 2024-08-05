package com.samuelokello.shopspot.ui.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.samuelokello.shopspot.R
import com.samuelokello.shopspot.data.Product
import com.samuelokello.shopspot.ui.navigation.bottom_navigation.BottomNavigationBar
import com.samuelokello.shopspot.ui.theme.primaryLight

/**
 * @Product Screen - Lists all available products
 */
@Composable
fun ProductScreen(viewModel: ProductViewModel, navController: NavController) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadProducts()
    }

    when {
        isLoading -> LoadingIndicator()
        else -> ProductList(
            products,
            viewModel,
            navController
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(products: List<Product>, viewModel: ProductViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "ShopEZ",
                        style = MaterialTheme.typography.titleLarge
                            .copy(
                                color = primaryLight,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold
                            )
                    )
                },
            )
        },

    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = Modifier.padding(innerPadding)
        ) {
            items(products) { product ->
                ProductItem(
                    product,
                    productViewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun LoadingIndicator() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
