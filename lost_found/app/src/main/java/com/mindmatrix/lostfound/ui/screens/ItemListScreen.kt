package com.mindmatrix.lostfound.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mindmatrix.lostfound.model.ItemSummary
import com.mindmatrix.lostfound.viewmodel.ItemViewModel

/**
 * ItemListScreen – scrollable list of all reported items (lost + found).
 *
 * Features:
 *  - Live search bar that filters by name or location.
 *  - "Lost" / "Found" filter tabs.
 *  - [LazyColumn] of item cards with image, name, category, location, and status badge.
 *  - Tapping a card navigates to [ItemDetailScreen].
 *
 * @param viewModel    [ItemViewModel] providing items and search query.
 * @param onItemClick  Callback with the selected item's ID.
 * @param onBack       Pop back to the previous screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    viewModel: ItemViewModel,
    onItemClick: (String) -> Unit,
    onBack: () -> Unit
) {
    // Filter tab state: 0 = All, 1 = Lost, 2 = Found
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("All", "Lost", "Found")

    // Apply tab filter on top of the search-filtered list
    val displayItems = viewModel.filteredItems.let { list ->
        when (selectedTab) {
            1    -> list.filter { it.isLost }
            2    -> list.filter { !it.isLost }
            else -> list
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Browse Items", color = Color.White, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1565C0))
                )

                // Search bar
                Surface(color = Color(0xFF1565C0), modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value         = viewModel.searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        placeholder   = { Text("Search by name or location…", color = Color.White.copy(alpha = 0.7f)) },
                        leadingIcon   = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                        },
                        singleLine  = true,
                        shape       = RoundedCornerShape(12.dp),
                        colors      = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            focusedTextColor     = Color.White,
                            unfocusedTextColor   = Color.White,
                            cursorColor          = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }

                // Tab row
                TabRow(
                    selectedTabIndex   = selectedTab,
                    containerColor     = Color(0xFF1565C0),
                    contentColor       = Color.White,
                    indicator          = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier  = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color     = Color.White
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick  = { selectedTab = index },
                            text     = {
                                Text(
                                    text       = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->

        if (displayItems.isEmpty()) {
            // Empty state
            Box(
                modifier          = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment  = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔍", style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "No items found",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF9E9E9E)
                    )
                    if (viewModel.searchQuery.isNotBlank()) {
                        Text(
                            "Try a different search term",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFBBBBCC)
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F7FA))
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Count header
                item {
                    Text(
                        text  = "${displayItems.size} item${if (displayItems.size == 1) "" else "s"} found",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF777799)
                    )
                }

                items(displayItems, key = { it.id }) { item ->
                    ItemCard(item = item, onClick = { onItemClick(item.id) })
                }

                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

// ── Item Card composable ───────────────────────────────────────────────────────

/**
 * ItemCard – a Material 3 card showing a single [ItemSummary] in the list.
 *
 * Shows:
 *  - Thumbnail image (AsyncImage via Coil)
 *  - Status badge (Lost 🔴 / Found 🟢)
 *  - Item name + category
 *  - Location + date
 */
@Composable
fun ItemCard(item: ItemSummary, onClick: () -> Unit) {
    Card(
        onClick   = onClick,
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Thumbnail
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE8EAF6))
            ) {
                if (item.imageUrl != null) {
                    AsyncImage(
                        model             = item.imageUrl,
                        contentDescription = item.name,
                        contentScale      = ContentScale.Crop,
                        modifier          = Modifier.fillMaxSize()
                    )
                } else {
                    // Placeholder icon when no image
                    Box(
                        modifier         = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text  = when (item.category.label) {
                                "Electronics"      -> "📱"
                                "Clothing"         -> "👕"
                                "Accessories"      -> "💍"
                                "Documents"        -> "📄"
                                "Books & Stationery" -> "📚"
                                "Keys"             -> "🔑"
                                "Wallet / Purse"   -> "👛"
                                else               -> "📦"
                            },
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                // Status badge overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (item.isLost) Color(0xFFF44336) else Color(0xFF4CAF50))
                )
            }

            // Text content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = if (item.isLost) Color(0xFFFFF3E0) else Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text     = if (item.isLost) "LOST" else "FOUND",
                            color    = if (item.isLost) Color(0xFFF9A825) else Color(0xFF2E7D32),
                            style    = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text     = item.category.label,
                        style    = MaterialTheme.typography.labelSmall,
                        color    = Color(0xFF9E9E9E),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text       = item.name,
                    style      = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color      = Color(0xFF1A1A2E),
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text     = "📍 ${item.location}",
                    style    = MaterialTheme.typography.bodySmall,
                    color    = Color(0xFF555577),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text  = "📅 ${item.date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9E9E9E)
                )
            }

            Text(text = "›", style = MaterialTheme.typography.titleLarge, color = Color(0xFFBBBBCC))
        }
    }
}
