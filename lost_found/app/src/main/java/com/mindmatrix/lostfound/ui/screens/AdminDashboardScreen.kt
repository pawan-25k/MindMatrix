package com.mindmatrix.lostfound.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindmatrix.lostfound.model.User
import com.mindmatrix.lostfound.viewmodel.ItemViewModel

/**
 * AdminDashboardScreen – simplified administration view.
 *
 * Shows aggregate stats (total lost, found, resolved) and a recent items list.
 * All moderation actions (delete, mark resolved, etc.) are stubbed with TODO
 * comments so they can be wired to backend calls without changing the layout.
 *
 * @param user         The admin user currently logged in.
 * @param viewModel    [ItemViewModel] for item data.
 * @param onViewItems  Navigate to the full item list.
 * @param onLogout     Log out and return to the login screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    user: User?,
    viewModel: ItemViewModel,
    onViewItems: () -> Unit,
    onLogout: () -> Unit
) {
    val allItems   = viewModel.items
    val lostCount  = allItems.count { it.isLost }
    val foundCount = allItems.count { !it.isLost }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard", color = Color.White, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D47A1))
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ── Welcome banner ──────────────────────────────────────────────
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(20.dp),
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFF0D47A1))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(listOf(Color(0xFF0D47A1), Color(0xFF1A237E)))
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Text("Admin Panel", color = Color.White.copy(alpha = 0.8f))
                            Text(
                                text       = user?.displayName ?: "Administrator",
                                color      = Color.White,
                                style      = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                "Manage all campus lost & found reports",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                        Text("🛡️", fontSize = 48.sp, modifier = Modifier.align(Alignment.TopEnd))
                    }
                }
            }

            // ── Stats row ───────────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AdminStatCard("Lost", lostCount.toString(), Color(0xFFFFF3E0), "🔴", Modifier.weight(1f))
                    AdminStatCard("Found", foundCount.toString(), Color(0xFFE8F5E9), "🟢", Modifier.weight(1f))
                    AdminStatCard("Total", allItems.size.toString(), Color(0xFFE3F2FD), "📋", Modifier.weight(1f))
                }
            }

            // ── Actions ─────────────────────────────────────────────────────
            item {
                Text(
                    "Quick Actions",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color      = Color(0xFF1A1A2E)
                )
            }

            item {
                Card(
                    onClick   = onViewItems,
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(14.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = Color(0xFF1565C0), modifier = Modifier.size(28.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("View All Items", fontWeight = FontWeight.Bold)
                            Text("Browse, search, and filter all reports", style = MaterialTheme.typography.bodySmall, color = Color(0xFF9E9E9E))
                        }
                        Text("›", fontSize = 22.sp, color = Color(0xFFBBBBCC))
                    }
                }
            }

            // ── Recent items ────────────────────────────────────────────────
            item {
                Text(
                    "Recent Reports",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color      = Color(0xFF1A1A2E)
                )
            }

            items(allItems.take(5)) { item ->
                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(12.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text  = if (item.isLost) "🔴" else "🟢",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
                            Text(item.location, style = MaterialTheme.typography.bodySmall, color = Color(0xFF9E9E9E))
                        }
                        Text(
                            text  = item.date,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFBBBBCC)
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun AdminStatCard(
    label: String,
    count: String,
    bgColor: Color,
    emoji: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 24.sp)
            Text(count, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
            Text(label, style = MaterialTheme.typography.bodySmall, color = Color(0xFF555577))
        }
    }
}
