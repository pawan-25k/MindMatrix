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
 * ReportLostItemScreen – form to submit a lost item report.
 *
 * Fields: name, description, category (dropdown), location, date, image upload (stub).
 *
 * @param viewModel       [ItemViewModel] that owns the form state.
 * @param currentUser     Currently logged-in user (for contact info).
 * @param onBack          Navigate back (pop this screen).
 * @param onSubmitSuccess Navigate away after a successful submission.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportLostItemScreen(
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
                title = { Text("Report Lost Item", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9A825))
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

            // Header label
            AnimatedVisibility(visible = visible, enter = fadeIn()) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("🔴", style = MaterialTheme.typography.titleLarge)
                        Column {
                            Text(
                                "Lost Item Report",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7B5800)
                            )
                            Text(
                                "Fill in the details below. The more information you provide, the better the chance of recovery.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFBF8C00)
                            )
                        }
                    }
                }
            }

            // ── Item Name ────────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.lostItemName,
                onValueChange = { viewModel.lostItemName = it },
                label         = "Item Name *",
                placeholder   = "e.g. Samsung Galaxy S24",
                leadingEmoji  = "📦"
            )

            // ── Description ──────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.lostItemDescription,
                onValueChange = { viewModel.lostItemDescription = it },
                label         = "Description",
                placeholder   = "Colour, brand, distinguishing features…",
                leadingEmoji  = "📝",
                singleLine    = false,
                minLines      = 3
            )

            // ── Category dropdown ─────────────────────────────────────────────
            ExposedDropdownMenuBox(
                expanded        = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    value         = viewModel.lostItemCategory.label,
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
                                viewModel.lostItemCategory = cat
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // ── Location ──────────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.lostItemLocation,
                onValueChange = { viewModel.lostItemLocation = it },
                label         = "Last Known Location *",
                placeholder   = "e.g. Central Library – 2nd Floor",
                leadingEmoji  = "📍"
            )

            // ── Date Lost ────────────────────────────────────────────────────
            FormTextField(
                value         = viewModel.lostItemDate,
                onValueChange = { viewModel.lostItemDate = it },
                label         = "Date Lost * (YYYY-MM-DD)",
                placeholder   = "2026-03-14",
                leadingEmoji  = "📅"
            )

            // ── Image upload (stub) ───────────────────────────────────────────
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(12.dp),
                border   = CardDefaults.outlinedCardBorder()
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
                    Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color(0xFF1565C0))
                }
            }

            // ── Error message ─────────────────────────────────────────────────
            viewModel.lostFormError?.let { msg ->
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

            // ── Submit button ─────────────────────────────────────────────────
            Button(
                onClick  = { viewModel.submitLostItem(currentUser, onSubmitSuccess) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9A825))
            ) {
                Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Submit Lost Item Report", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ── Reusable form text field ──────────────────────────────────────────────────

/**
 * FormTextField – a pre-styled [OutlinedTextField] used across all form screens.
 */
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingEmoji: String,
    singleLine: Boolean = true,
    minLines: Int = 1,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        label         = { Text(label) },
        placeholder   = { Text(placeholder, color = Color(0xFFBBBBCC)) },
        leadingIcon   = {
            Text(
                text     = leadingEmoji,
                modifier = Modifier.padding(start = 8.dp),
                style    = MaterialTheme.typography.titleMedium
            )
        },
        singleLine  = singleLine,
        minLines    = minLines,
        shape       = RoundedCornerShape(12.dp),
        modifier    = modifier.fillMaxWidth()
    )
}
