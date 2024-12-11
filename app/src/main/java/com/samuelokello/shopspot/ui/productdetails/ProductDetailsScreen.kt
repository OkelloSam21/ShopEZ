package com.samuelokello.shopspot.ui.productdetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.samuelokello.shopspot.domain.Product
import com.samuelokello.shopspot.ui.AppViewModelProvider

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(
    viewModel:ProductDetailViewModel = viewModel(factory= AppViewModelProvider.Factory),
    productId: Int
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProductById(productId)
    }


    when(state) {
        is ProductDetailUiState.Loading -> {
            CircularProgressIndicator()
        }

        is  ProductDetailUiState.Success -> {
            ProductDetail(modifier = Modifier,state = state)
        }

        is ProductDetailUiState.Error -> {
            Text(text = (state as ProductDetailUiState.Error).message)
        }
    }
}

@Composable
fun ProductDetail(modifier: Modifier = Modifier, state: ProductDetailUiState) {
    Column (
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val productDetails = (state as ProductDetailUiState.Success).product
        AsyncImage(
            model = productDetails.image,
            contentDescription = null,
            modifier = modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = modifier.height(8.dp))

        Column(
            modifier = modifier.padding(4.dp),
        ) {
            Row {
                Text(
                    text = productDetails.title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.StarRate,
                    contentDescription = "",
                    modifier = Modifier,
                    tint = Color.Red
                )
                Text(text = productDetails.rating.toString())
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = productDetails.count.toString(),
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Text(
                text = productDetails.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(2f))
        Column {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$ ${productDetails.price}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(.5f))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Add to Cart")
                }
            }
        }
        Spacer(modifier= modifier.height(16.dp))
    }
}

