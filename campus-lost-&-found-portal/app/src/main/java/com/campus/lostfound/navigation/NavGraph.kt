package com.campus.lostfound.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.campus.lostfound.data.repository.MockAuthRepository
import com.campus.lostfound.data.repository.MockItemRepository
import com.campus.lostfound.models.ItemType
import com.campus.lostfound.ui.screens.*
import com.campus.lostfound.ui.viewmodels.AuthViewModel
import com.campus.lostfound.ui.viewmodels.AuthViewModelFactory
import com.campus.lostfound.ui.viewmodels.ItemViewModel
import com.campus.lostfound.ui.viewmodels.ItemViewModelFactory

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    
    // In a real app, these would be provided by Dependency Injection (Hilt/Koin)
    val authRepository = remember { MockAuthRepository() }
    val itemRepository = remember { MockItemRepository() }
    
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
    val itemViewModel: ItemViewModel = viewModel(factory = ItemViewModelFactory(itemRepository))

    val currentUser by authViewModel.currentUser.collectAsState(initial = null)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(authViewModel) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable("home") {
            HomeScreen(
                user = currentUser,
                onNavigateToReportLost = { navController.navigate("report_lost") },
                onNavigateToReportFound = { navController.navigate("report_found") },
                onNavigateToViewItems = { navController.navigate("item_list") },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
        composable("report_lost") {
            ReportLostScreen(
                viewModel = itemViewModel,
                user = currentUser,
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
        composable("report_found") {
            ReportFoundScreen(
                viewModel = itemViewModel,
                user = currentUser,
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
        composable("item_list") {
            val items by itemViewModel.items.collectAsState()
            ItemListScreen(
                items = items,
                onBack = { navController.popBackStack() },
                onItemClick = { item ->
                    navController.navigate("item_detail/${item.id}")
                }
            )
        }
        composable(
            "item_detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            val items by itemViewModel.items.collectAsState()
            val item = items.find { it.id == itemId }
            if (item != null) {
                ItemDetailScreen(item = item, onBack = { navController.popBackStack() })
            }
        }
    }
}
