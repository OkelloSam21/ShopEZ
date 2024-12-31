package com.samuelokello.shopspot.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samuelokello.shopspot.ui.AppViewModelProvider

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { route ->
            if (route == "home") navigateToHome()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onUsernameChange = { viewModel.onEvent(LoginEvent.UsernameChanged(it)) },
        onPasswordChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
        onRememberMeChange = { viewModel.onEvent(LoginEvent.RememberMeChanged(it)) },
        onForgotPasswordClick = navigateToForgotPassword,
        onRegisterClick = navigateToRegister,
        onSignInClick = {
            keyboardController?.hide()
            viewModel.onEvent(LoginEvent.Submit)
        }
    )
}


@Composable
private fun LoginScreenContent(
    uiState: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
    ) {
        item { Text(text = "Welcome Back") }

        item {
            Spacer(modifier = Modifier.height(64.dp))
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.username,
                    onValueChange = onUsernameChange,
                    label = { Text(text = "Username") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    maxLines = 1,
                    singleLine = true,
                    isError = uiState.usernameError != null
                )
                uiState.usernameError?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    label = { Text(text = "Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    maxLines = 1,
                    singleLine = true,
                    isError = uiState.passwordError != null
                )
                uiState.passwordError?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = uiState.rememberMe,
                        onCheckedChange = onRememberMeChange
                    )
                    Text(text = "Remember me", fontSize = 12.sp)
                }
                TextButton(onClick = onForgotPasswordClick) {
                    Text(text = "Forgot password?")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onSignInClick,
                shape = RoundedCornerShape(8),
                enabled = !uiState.isLoading
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = "Sign In",
                    textAlign = TextAlign.Center
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Sign Up")
                        }
                    },
                    textAlign = TextAlign.Center
                )
            }
        }

        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }

        uiState.error?.let { error ->
            item {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}