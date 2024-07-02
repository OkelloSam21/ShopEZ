package com.samuelokello.shopspot.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samuelokello.shopspot.ui.navigation.Screens
import com.samuelokello.shopspot.ui.products.ProductViewModel
import com.samuelokello.shopspot.ui.theme.onPrimaryLight
import com.samuelokello.shopspot.ui.theme.primaryLight

@Composable
fun OrderPlacedScreen(navController: NavController, viewModel: ProductViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Order Placed",
            modifier = Modifier.size(100.dp),
            tint = primaryLight
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Order Placed Successfully!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Thank you for your purchase.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                viewModel.clearCart() // Clear the cart
                navController.navigate(Screens.Products.route) {
                    popUpTo(Screens.OrderPlaced.route) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryLight,
                contentColor = onPrimaryLight
            ),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Return to Products")
        }
    }
}