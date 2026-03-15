package com.mindmatrix.lostfound.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mindmatrix.lostfound.model.ItemCategory
import com.mindmatrix.lostfound.model.User
import com.mindmatrix.lostfound.viewmodel.ItemViewModel

/**
 * ReportFoundItemScreen – form to submit a found item report.
 *
 * Almost identical in layout to [ReportLostItemScreen] but with a teal/green
 * colour theme and an extra "Handed to admin" toggle.
 *
 * @param viewModel       [ItemViewModel] that owns the found-item form state.
 * @param currentUser     Currently logged-in user (for contact info).
 * @param onBack          Navigate back (pop this screen).
 * @param onSubmitSuccess Navigate away after a successful submission.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportFoundItemScreen(
    viewModel: ItemViewModel,
    currentUser: User?,
    onBack: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    var categoryExpanded by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Found Item", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32))
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Header info card
            AnimatedVisibility(visible = visible, enter = fadeIn()) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("🟢", style = MaterialTheme.typography.titleLarge)
                        Column {
                            Text(
                                "Found Item Report",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                "Thank you for helping! Please describe the item so the owner can identify it.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }
            }

            // ── Item Name ──────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.foundItemName,
                onValueChange = { viewModel.foundItemName = it },
                label         = "Item Name *",
                placeholder   = "e.g. Black Leather Wallet",
                leadingEmoji  = "📦"
            )

            // ── Description ────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.foundItemDescription,
                onValueChange = { viewModel.foundItemDescription = it },
                label         = "Description",
                placeholder   = "Colour, contents, brand, distinguishing marks…",
                leadingEmoji  = "📝",
                singleLine    = false,
                minLines      = 3
            )

            // ── Category dropdown ──────────────────────────────────────────
            ExposedDropdownMenuBox(
                expanded         = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    value         = viewModel.foundItemCategory.label,
                    onValueChange = {},
                    readOnly      = true,
                    label         = { Text("Category") },
                    leadingIcon   = { Text("🏷️", modifier = Modifier.padding(start = 4.dp)) },
                    trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier      = Modifier.fillMaxWidth().menuAnchor(),
                    shape         = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded        = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    ItemCategory.entries.forEach { cat ->
                        DropdownMenuItem(
                            text    = { Text(cat.label) },
                            onClick = {
                                viewModel.foundItemCategory = cat
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // ── Location ───────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.foundItemLocation,
                onValueChange = { viewModel.foundItemLocation = it },
                label         = "Location Found *",
                placeholder   = "e.g. Sports Complex – Main Gate",
                leadingEmoji  = "📍"
            )

            // ── Date Found ─────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.foundItemDate,
                onValueChange = { viewModel.foundItemDate = it },
                label         = "Date Found * (YYYY-MM-DD)",
                placeholder   = "2026-03-14",
                leadingEmoji  = "📅"
            )

            // ── Image upload (stub) ────────────────────────────────────────
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(12.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("📷", style = MaterialTheme.typography.titleLarge)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Add Photo (optional)",
                            style      = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "Tap to pick from gallery or take a photo",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9E9E9E)
                        )
                    }
                    // TODO: Implement ActivityResultContracts.GetContent() for real image picking
                    Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color(0xFF2E7D32))
                }
            }

            // ── Handed to admin switch ─────────────────────────────────────
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("🏛️", style = MaterialTheme.typography.titleLarge)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Handed to Admin Office", fontWeight = FontWeight.Medium)
                        Text(
                            "Enable if you've already deposited the item.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9E9E9E)
                        )
                    }
                    Switch(
                        checked         = viewModel.foundHandedToAdmin,
                        onCheckedChange = { viewModel.foundHandedToAdmin = it },
                        colors          = SwitchDefaults.colors(
                            checkedThumbColor      = Color.White,
                            checkedTrackColor      = Color(0xFF2E7D32)
                        )
                    )
                }
            }

            // ── Error message ──────────────────────────────────────────────
            viewModel.foundFormError?.let { msg ->
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

            // ── Submit button ──────────────────────────────────────────────
            Button(
                onClick  = { viewModel.submitFoundItem(currentUser, onSubmitSuccess) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Submit Found Item Report", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
