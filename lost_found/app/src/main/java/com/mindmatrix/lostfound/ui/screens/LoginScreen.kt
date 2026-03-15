package com.mindmatrix.lostfound.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindmatrix.lostfound.model.User
import com.mindmatrix.lostfound.model.UserRole
import com.mindmatrix.lostfound.viewmodel.LoginViewModel

/**
 * LoginScreen – entry point for both Student and Admin users.
 *
 * Layout:
 *  - Full-screen gradient background
 *  - Animated headline + icon
 *  - Card with email/password fields + role selector
 *  - Error message (animated visibility)
 *  - Login button with loading indicator
 *
 * @param viewModel      [LoginViewModel] that owns all form state.
 * @param onLoginSuccess Callback invoked with the authenticated [User].
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (User) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }

    // Entrance animation flag
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1565C0),
                        Color(0xFF0D2B6E),
                        Color(0xFF0A1628)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ── Hero section ─────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -60 })
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🎓",
                        fontSize = 64.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Campus Lost & Found",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Reuniting students with their belongings",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(36.dp))

            // ── Login card ───────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 80 })
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A2E)
                        )

                        // ── Email field ──────────────────────────────────────
                        OutlinedTextField(
                            value         = viewModel.email,
                            onValueChange = viewModel::onEmailChange,
                            label         = { Text("Email Address") },
                            leadingIcon   = {
                                Icon(Icons.Default.Email, contentDescription = null,
                                    tint = Color(0xFF1565C0))
                            },
                            singleLine    = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction    = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            ),
                            shape  = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // ── Password field ───────────────────────────────────
                        OutlinedTextField(
                            value         = viewModel.password,
                            onValueChange = viewModel::onPasswordChange,
                            label         = { Text("Password") },
                            leadingIcon   = {
                                Icon(Icons.Default.Lock, contentDescription = null,
                                    tint = Color(0xFF1565C0))
                            },
                            trailingIcon  = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible)
                                            Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (passwordVisible)
                                            "Hide password" else "Show password"
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction    = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            shape    = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // ── Role selector ────────────────────────────────────
                        Text(
                            text  = "Login as",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFF555577)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            UserRole.entries.forEach { role ->
                                val selected = viewModel.selectedRole == role
                                FilterChip(
                                    selected = selected,
                                    onClick  = { viewModel.onRoleChange(role) },
                                    label    = {
                                        Text(
                                            text = role.name.lowercase()
                                                .replaceFirstChar { it.uppercaseChar() },
                                            fontWeight = if (selected) FontWeight.Bold
                                                         else FontWeight.Normal
                                        )
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF1565C0),
                                        selectedLabelColor     = Color.White
                                    )
                                )
                            }
                        }

                        // ── Error message ────────────────────────────────────
                        AnimatedVisibility(visible = viewModel.errorMessage != null) {
                            viewModel.errorMessage?.let { msg ->
                                Surface(
                                    color  = MaterialTheme.colorScheme.errorContainer,
                                    shape  = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text     = msg,
                                        color    = MaterialTheme.colorScheme.onErrorContainer,
                                        modifier = Modifier.padding(12.dp),
                                        style    = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }

                        // ── Login button ─────────────────────────────────────
                        Button(
                            onClick  = { viewModel.login(onSuccess = onLoginSuccess) },
                            enabled  = !viewModel.isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1565C0)
                            )
                        ) {
                            if (viewModel.isLoading) {
                                CircularProgressIndicator(
                                    color     = Color.White,
                                    modifier  = Modifier.size(22.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text       = "Login",
                                    fontWeight = FontWeight.Bold,
                                    fontSize   = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            // Add this below your Login Button in LoginScreen.kt
            TextButton(onClick = { /* Navigate to Register */ }) {
                Text("Don't have an account? Register here", color = Color.White.copy(alpha = 0.8f))
            }

            Spacer(Modifier.height(24.dp))

            // Footer note
            Text(
                text  = "Use your university email to sign in.\nContact admin if you need account access.",
                color = Color.White.copy(alpha = 0.55f),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
