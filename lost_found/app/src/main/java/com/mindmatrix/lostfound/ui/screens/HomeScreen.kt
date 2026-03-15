package com.mindmatrix.lostfound.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindmatrix.lostfound.model.User

/**
 * HomeScreen – the main hub after login.
 *
 * Displays a personalised welcome banner and three action cards:
 *   1. Report Lost Item
 *   2. Report Found Item
 *   3. View All Items
 *
 * @param user          Currently logged-in user (may be null briefly on recomposition).
 * @param onReportLost  Navigate to the Report Lost screen.
 * @param onReportFound Navigate to the Report Found screen.
 * @param onViewItems   Navigate to the Item List screen.
 * @param onLogout      Clear session and return to Login.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: User?,
    onReportLost: () -> Unit,
    onReportFound: () -> Unit,
    onViewItems: () -> Unit,
    onLogout: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text       = "Lost & Found",
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0)
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── Welcome banner ────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -40 })
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF1565C0), Color(0xFF0D47A1))
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Text(
                                text = "Welcome back,",
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text  = user?.displayName ?: "Student",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.height(8.dp))
                            Surface(
                                color  = Color.White.copy(alpha = 0.2f),
                                shape  = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text     = "Role: ${user?.role?.name ?: ""}",
                                    color    = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    style    = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                        // Decorative emoji badge in top-right
                        Text(
                            text     = "🔍",
                            fontSize = 48.sp,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                }
            }

            // ── Stats row ─────────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 30 })
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        label  = "Lost Items",
                        count  = "5",
                        color  = Color(0xFFFFF3E0),
                        badge  = "🔴",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label  = "Found Items",
                        count  = "4",
                        color  = Color(0xFFE8F5E9),
                        badge  = "🟢",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ── Section heading ───────────────────────────────────────────────
            Text(
                text       = "What would you like to do?",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color      = Color(0xFF1A1A2E)
            )

            // ── Action cards ──────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 60 })
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    ActionCard(
                        icon        = Icons.Default.FindInPage,
                        title       = "Report a Lost Item",
                        subtitle    = "Missing something? File a report quickly.",
                        iconBgColor = Color(0xFFFFF9C4),
                        iconTint    = Color(0xFFF9A825),
                        onClick     = onReportLost
                    )
                    ActionCard(
                        icon        = Icons.Default.AddCircle,
                        title       = "Report a Found Item",
                        subtitle    = "Found something? Help return it to its owner.",
                        iconBgColor = Color(0xFFC8E6C9),
                        iconTint    = Color(0xFF2E7D32),
                        onClick     = onReportFound
                    )
                    ActionCard(
                        icon        = Icons.AutoMirrored.Filled.List,
                        title       = "Browse All Items",
                        subtitle    = "Search through all lost & found reports.",
                        iconBgColor = Color(0xFFBBDEFB),
                        iconTint    = Color(0xFF1565C0),
                        onClick     = onViewItems
                    )
                }
            }

            // ── Footer ────────────────────────────────────────────────────────
            Spacer(Modifier.height(8.dp))
            Text(
                text      = "📍 MindMatrix University · Campus Safety Portal",
                style     = MaterialTheme.typography.bodySmall,
                color     = Color(0xFF9E9E9E),
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )
        }
    }
}

// ── Private composable helpers ────────────────────────────────────────────────

@Composable
private fun StatCard(
    label: String,
    count: String,
    color: Color,
    badge: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = badge, fontSize = 28.sp)
            Text(
                text       = count,
                style      = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color      = Color(0xFF1A1A2E)
            )
            Text(
                text  = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF555577)
            )
        }
    }
}

@Composable
private fun ActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconBgColor: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Card(
        onClick   = onClick,
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    tint               = iconTint,
                    modifier           = Modifier.size(28.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = title,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color      = Color(0xFF1A1A2E)
                )
                Text(
                    text  = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF777799)
                )
            }
            Text(text = "›", fontSize = 24.sp, color = Color(0xFFBBBBCC))
        }
    }
}
