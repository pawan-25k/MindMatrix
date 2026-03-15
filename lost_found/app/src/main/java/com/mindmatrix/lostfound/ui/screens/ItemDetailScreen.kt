package com.mindmatrix.lostfound.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mindmatrix.lostfound.model.ItemSummary
import com.mindmatrix.lostfound.viewmodel.ItemViewModel

/**
 * ItemDetailScreen – full details for a single item selected from the list.
 *
 * Displays:
 *  - Hero image (async, with placeholder)
 *  - Status badge (Lost / Found) + category chip
 *  - Item name, description, location, date
 *  - Reporter contact card with email and phone
 *  - "Contact Reporter" action button
 *
 * @param itemId   The ID of the item to display.
 * @param viewModel [ItemViewModel] used to look up the item.
 * @param onBack   Pop back to the previous screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    viewModel: ItemViewModel,
    onBack: () -> Unit
) {
    val item: ItemSummary? = remember(itemId) { viewModel.getItemById(itemId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Item Details", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1565C0))
            )
        }
    ) { padding ->

        if (item == null) {
            // Item not found state
            Box(
                modifier         = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("❓", fontSize = 64.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("Item not found", style = MaterialTheme.typography.titleMedium, color = Color(0xFF9E9E9E))
                }
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            // ── Hero image panel ──────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color(0xFFE8EAF6))
            ) {
                if (item.imageUrl != null) {
                    AsyncImage(
                        model              = item.imageUrl,
                        contentDescription = item.name,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )
                } else {
                    // Placeholder emoji when no image available
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = when (item.category.label) {
                                "Electronics"        -> "📱"
                                "Clothing"           -> "👕"
                                "Accessories"        -> "💍"
                                "Documents"          -> "📄"
                                "Books & Stationery" -> "📚"
                                "Keys"               -> "🔑"
                                "Wallet / Purse"     -> "👛"
                                else                 -> "📦"
                            },
                            fontSize = 96.sp
                        )
                    }
                }

                // Status badge overlay (bottom-left of image)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Surface(
                        color  = if (item.isLost) Color(0xFFF9A825) else Color(0xFF2E7D32),
                        shape  = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text       = if (item.isLost) "🔴  LOST" else "🟢  FOUND",
                            color      = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            modifier   = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // ── Body content ──────────────────────────────────────────────────
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Category chip + item name
                Column {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text     = item.category.label,
                            color    = MaterialTheme.colorScheme.onPrimaryContainer,
                            style    = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text       = item.name,
                        style      = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color      = Color(0xFF1A1A2E)
                    )
                }

                HorizontalDivider(color = Color(0xFFE0E0E0))

                // Description
                if (item.description.isNotBlank()) {
                    DetailSection(
                        icon    = "📝",
                        heading = "Description",
                        body    = item.description
                    )
                }

                // Location
                DetailSection(
                    icon    = "📍",
                    heading = if (item.isLost) "Last Known Location" else "Location Found",
                    body    = item.location
                )

                // Date
                DetailSection(
                    icon    = "📅",
                    heading = if (item.isLost) "Date Lost" else "Date Found",
                    body    = item.date
                )

                HorizontalDivider(color = Color(0xFFE0E0E0))

                // ── Reporter contact card ─────────────────────────────────────
                Card(
                    shape  = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text       = "Reporter Contact",
                            style      = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color      = Color(0xFF1A1A2E)
                        )

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFE3F2FD)),
                                contentAlignment = Alignment.Center
                            ) { Text("👤", fontSize = 20.sp) }
                            Column {
                                Text(item.reportedBy, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
                                Text(
                                    if (item.isLost) "Lost item reporter" else "Found item reporter",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF9E9E9E)
                                )
                            }
                        }

                        // Email row
                        if (item.contactEmail.isNotBlank()) {
                            ContactRow(
                                icon  = { Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFF1565C0), modifier = Modifier.size(18.dp)) },
                                label = item.contactEmail
                            )
                        }

                        // Phone row
                        if (item.contactPhone.isNotBlank()) {
                            ContactRow(
                                icon  = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp)) },
                                label = item.contactPhone
                            )
                        }
                    }
                }

                // ── Contact button ────────────────────────────────────────────
                Button(
                    onClick  = {
                        // TODO: Launch email intent – Intent.ACTION_SENDTO with mailto: URI
                        //   val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${item.contactEmail}"))
                        //   context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text("Contact Reporter", fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

// ── Small helper composables ──────────────────────────────────────────────────

@Composable
private fun DetailSection(icon: String, heading: String, body: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(icon, style = MaterialTheme.typography.titleSmall)
            Text(
                text       = heading,
                style      = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color      = Color(0xFF555577)
            )
        }
        Text(
            text     = body,
            style    = MaterialTheme.typography.bodyMedium,
            color    = Color(0xFF1A1A2E),
            modifier = Modifier.padding(start = 28.dp)
        )
    }
}

@Composable
private fun ContactRow(icon: @Composable () -> Unit, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        icon()
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF1A1A2E))
    }
}
