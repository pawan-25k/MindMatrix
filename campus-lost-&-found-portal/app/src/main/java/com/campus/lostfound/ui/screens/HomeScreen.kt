package com.campus.lostfound.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.campus.lostfound.models.User

@Composable
fun HomeScreen(
    user: User?,
    onNavigateToReportLost: () -> Unit,
    onNavigateToReportFound: () -> Unit,
    onNavigateToViewItems: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onLogout) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Welcome, ${user?.displayName ?: user?.role}!",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "What would you like to do today?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        MenuCard(
            title = "Report Lost Item",
            description = "Lost something? Let the campus know.",
            icon = Icons.Default.Search,
            onClick = onNavigateToReportLost
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuCard(
            title = "Report Found Item",
            description = "Found something? Help it find its owner.",
            icon = Icons.Default.Add,
            onClick = onNavigateToReportFound
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuCard(
            title = "View All Items",
            description = "Browse all reported lost and found items.",
            icon = Icons.Default.List,
            onClick = onNavigateToViewItems
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(text = description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
