package com.mindmatrix.lostfound.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mindmatrix.lostfound.ui.screens.AdminDashboardScreen
import com.mindmatrix.lostfound.ui.screens.HomeScreen
import com.mindmatrix.lostfound.ui.screens.ItemDetailScreen
import com.mindmatrix.lostfound.ui.screens.ItemListScreen
import com.mindmatrix.lostfound.ui.screens.LoginScreen
import com.mindmatrix.lostfound.ui.screens.ReportFoundItemScreen
import com.mindmatrix.lostfound.ui.screens.ReportLostItemScreen
import com.mindmatrix.lostfound.viewmodel.ItemViewModel
import com.mindmatrix.lostfound.viewmodel.LoginViewModel

/**
 * AppNavGraph – sets up the Compose Navigation graph for the entire app.
 *
 * Each composable {} block corresponds to a screen in [Screen].
 * ViewModels are scoped at the NavHost level so they survive recomposition
 * but are re-created when the whole graph is recreated (e.g., process death).
 *
 * @param navController The NavHostController that drives navigation.
 * @param modifier       Optional modifier applied to the NavHost root.
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Shared ViewModels – scoped to the NavHost lifecycle
    val loginViewModel: LoginViewModel = viewModel()
    val itemViewModel: ItemViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {

        // ── Login Screen ─────────────────────────────────────────────────────
        composable(route = Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { user ->
                    // Route admin to admin dashboard; students go to home
                    if (user.role.name == "ADMIN") {
                        navController.navigate(Screen.AdminDashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                viewModel = loginViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // ── Home Screen ──────────────────────────────────────────────────────
        composable(route = Screen.Home.route) {
            HomeScreen(
                user = loginViewModel.currentUser,
                onReportLost    = { navController.navigate(Screen.ReportLost.route) },
                onReportFound   = { navController.navigate(Screen.ReportFound.route) },
                onViewItems     = { navController.navigate(Screen.ItemList.route) },
                onLogout        = {
                    loginViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── Report Lost Item Screen ──────────────────────────────────────────
        composable(route = Screen.ReportLost.route) {
            ReportLostItemScreen(
                viewModel  = itemViewModel,
                currentUser = loginViewModel.currentUser,
                onBack     = { navController.popBackStack() },
                onSubmitSuccess = {
                    navController.navigate(Screen.ItemList.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        // ── Report Found Item Screen ─────────────────────────────────────────
        composable(route = Screen.ReportFound.route) {
            ReportFoundItemScreen(
                viewModel   = itemViewModel,
                currentUser = loginViewModel.currentUser,
                onBack      = { navController.popBackStack() },
                onSubmitSuccess = {
                    navController.navigate(Screen.ItemList.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        // ── Item List Screen ─────────────────────────────────────────────────
        composable(route = Screen.ItemList.route) {
            ItemListScreen(
                viewModel  = itemViewModel,
                onItemClick = { itemId ->
                    navController.navigate(Screen.ItemDetail.createRoute(itemId))
                },
                onBack     = { navController.popBackStack() }
            )
        }

        // ── Item Detail Screen (receives itemId as a path argument) ──────────
        composable(
            route = Screen.ItemDetail.route,
            arguments = listOf(
                navArgument(Screen.ItemDetail.ARG_ITEM_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Screen.ItemDetail.ARG_ITEM_ID) ?: ""
            ItemDetailScreen(
                itemId    = itemId,
                viewModel = itemViewModel,
                onBack    = { navController.popBackStack() }
            )
        }

        // ── Admin Dashboard Screen ───────────────────────────────────────────
        composable(route = Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                user       = loginViewModel.currentUser,
                viewModel  = itemViewModel,
                onViewItems = { navController.navigate(Screen.ItemList.route) },
                onLogout   = {
                    loginViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun RegisterScreen(
    viewModel: LoginViewModel,
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Boolean
) {
    TODO("Not yet implemented")
}
