package com.campus.lostfound.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.campus.lostfound.models.ItemType
import com.campus.lostfound.models.User
import com.campus.lostfound.ui.viewmodels.ItemViewModel
import com.campus.lostfound.ui.viewmodels.ReportState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportLostScreen(
    viewModel: ItemViewModel,
    user: User?,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var dateLost by remember { mutableStateOf("") }

    val reportState by viewModel.reportState.collectAsState()

    LaunchedEffect(reportState) {
        if (reportState is ReportState.Success) {
            viewModel.resetReportState()
            onSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Lost Item") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (reportState is ReportState.Error) {
                Text(
                    text = (reportState as ReportState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth(),
                enabled = reportState !is ReportState.Loading
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                enabled = reportState !is ReportState.Loading
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category (e.g. Electronics, Keys)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = reportState !is ReportState.Loading
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location Lost") },
                modifier = Modifier.fillMaxWidth(),
                enabled = reportState !is ReportState.Loading
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = dateLost,
                onValueChange = { dateLost = it },
                label = { Text("Date Lost") },
                modifier = Modifier.fillMaxWidth(),
                enabled = reportState !is ReportState.Loading
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { /* Handle image upload logic */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                enabled = reportState !is ReportState.Loading
            ) {
                Text("Upload Image (Optional)")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.reportItem(
                        itemName, description, category, location, dateLost,
                        ItemType.LOST, user?.email ?: "anonymous", user?.uid ?: ""
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = reportState !is ReportState.Loading
            ) {
                if (reportState is ReportState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Submit Report")
                }
            }
        }
    }
}
