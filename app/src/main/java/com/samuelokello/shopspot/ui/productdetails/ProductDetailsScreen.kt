package com.samuelokello.shopspot.ui.productdetails

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samuelokello.shopspot.R
import com.samuelokello.shopspot.data.Product
import com.samuelokello.shopspot.ui.theme.onSecondaryContainerLight
import com.samuelokello.shopspot.ui.theme.secondaryContainerLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsScreen(viewModel:ProductDetailViewModel,product: Product) {

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadProduct(product)
    }

    LaunchedEffect(product.image) {
        isLoading = true
        error = null
        try {
            val loadedBitmap = withContext(Dispatchers.IO) {
                val connection = URL(product.image).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                BitmapFactory.decodeStream(inputStream)
            }
            bitmap = loadedBitmap
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    when(state) {
        is ProductDetailUiState.Loading -> {
            CircularProgressIndicator()
        }

        is  ProductDetailUiState.Success -> {
            Column {
                val productDetails = (state as ProductDetailUiState.Success).product
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(250.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = secondaryContainerLight,
                            contentColor = onSecondaryContainerLight
                        )
                    ) {
                        when {
                            isLoading -> Box(Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            error != null -> Box(Modifier.fillMaxSize()) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_error_24),
                                    contentDescription = "Error",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            bitmap != null -> Image(
                                bitmap = bitmap!!.asImageBitmap(),
                                contentDescription = product.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Row {
                            Text(
                                text = productDetails.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = ""
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Column {
                            Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                Icon(
                                    Icons.Default.StarRate,
                                    contentDescription = "",
                                    modifier = Modifier,
                                    tint = Color.Red
                                )
                                Text(text = productDetails.rating?.rate.toString())
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = productDetails.rating?.count.toString(),
                                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = productDetails.description,
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Light
                        )
                        Spacer(modifier = Modifier.height(8.dp))



                        Row (
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
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
                }

            }
        }

        is ProductDetailUiState.Error -> {
            Text(text = (state as ProductDetailUiState.Error).message)
        }
    }
}
