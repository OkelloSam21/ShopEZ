package com.samuelokello.shopspot.ui.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit
) {
    RegisterScreenContent(
        onClickSignUp = {
            navigateToLogin()
        }
    )
}

@Composable
private fun RegisterScreenContent(
    onClickSignUp: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        item  {
            Text(text="Getting Started",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = "Create an account to continue with your shopping")
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Name")
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Email
                ),
                maxLines = 1,
                singleLine = true,
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Email
                ),
                maxLines = 1,
                singleLine = true,
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Password
                ),
                maxLines = 1,
                singleLine = true,
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))

            val context = LocalContext.current

            Button(
                onClick = {

                    Toast.makeText(
                        context,
                        "This API does not provide an endpoint for registering, just login with the credentials provided in the README file",
                        Toast.LENGTH_LONG
                    ).show()
                },
                shape = RoundedCornerShape(8)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), text = "Sign Up", textAlign = TextAlign.Center
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = onClickSignUp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account?")
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Sign In")
                        }
                    },
//                    fontFamily = poppins,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}