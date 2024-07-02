package com.samuelokello.shopspot.ui.products

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.samuelokello.shopspot.R
import com.samuelokello.shopspot.data.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ProductItem(product: Product) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                when {
                    isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    error != null -> Icon(
                        painter = painterResource(id = R.drawable.baseline_error_24),
                        contentDescription = "Error",
                        modifier = Modifier.align(Alignment.Center)
                    )
                    bitmap != null -> Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Add to cart functionality */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add to Cart")
            }
        }
    }
}