package com.samuelokello.shopspot.ui.checkout

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samuelokello.shopspot.R
import com.samuelokello.shopspot.ui.navigation.Screens
import com.samuelokello.shopspot.ui.products.ProductViewModel
import com.samuelokello.shopspot.ui.theme.onPrimaryLight
import com.samuelokello.shopspot.ui.theme.onSecondaryContainerLight
import com.samuelokello.shopspot.ui.theme.primaryLight
import com.samuelokello.shopspot.ui.theme.secondaryContainerLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    viewModel: ProductViewModel,
) {
    val cartItems by viewModel.cart.collectAsState()
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }

    LaunchedEffect(Unit) {
        Log.d("CheckoutScreen", "Cart items: ${cartItems.size}")
        cartItems.forEach { item ->
            Log.d("CheckoutScreen", "${item.product.title} - Quantity: ${item.quantity}")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Checkout",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = primaryLight,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (cartItems.isEmpty()) {
                EmptyCartMessage(navController)
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { cartItem ->
                        CheckoutItem(
                            cartItem = cartItem,
                            onIncreaseQuantity = { viewModel.increaseQuantity(cartItem.product) },
                            onDecreaseQuantity = { viewModel.decreaseQuantity(cartItem.product) },
                            onRemoveItem = { viewModel.removeFromCart(cartItem.product) }
                        )
                    }
                }
                TotalPriceRow(totalPrice)
                CheckoutButton(navController)
            }
        }
    }
}

@Composable
fun CheckoutItem(
    cartItem: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onRemoveItem: () -> Unit
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(cartItem.product.image) {
        isLoading = true
        error = null
        try {
            val loadedBitmap =
                withContext(Dispatchers.IO) {
                    val connection = URL(cartItem.product.image).openConnection() as HttpURLConnection
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = secondaryContainerLight,
            contentColor = onSecondaryContainerLight
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                when {
                    isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    error != null -> Icon(
                        painter = painterResource(id = R.drawable.baseline_error_24),
                        contentDescription = "Error",
                        modifier = Modifier.align(Alignment.Center),
                    )
                    bitmap != null -> Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = cartItem.product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = cartItem.product.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "$${cartItem.product.price}", style = MaterialTheme.typography.bodyMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onDecreaseQuantity) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                    }
                    Text(text = cartItem.quantity.toString(), style = MaterialTheme.typography.bodyLarge)
                    IconButton(onClick = onIncreaseQuantity) {
                        Icon(Icons.Default.Add, contentDescription = "Increase")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = onRemoveItem) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyCartMessage(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your cart is empty", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate(Screens.Products.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryLight,
                contentColor = onPrimaryLight
            ),
        ) {
            Text("Go to Products")
        }
    }
}

@Composable
fun TotalPriceRow(totalPrice: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Total:", style = MaterialTheme.typography.titleLarge)
        Text("$${String.format("%.2f", totalPrice)}", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun CheckoutButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Screens.OrderPlaced.route) },
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryLight,
            contentColor = onPrimaryLight
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Place Order")
    }
}