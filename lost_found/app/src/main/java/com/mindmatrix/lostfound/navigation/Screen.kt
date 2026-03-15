package com.mindmatrix.lostfound.navigation

/**
 * Screen – a sealed class that enumerates every destination in the app.
 *
 * Using a sealed class (instead of raw strings) means:
 *   - All routes are type-safe and centralised.
 *   - Refactoring a route name only requires one change here.
 *   - Arguments are expressed as typed properties, not magic strings.
 *
 * NavController.navigate() accepts the [route] property.
 */
sealed class Screen(val route: String) {

    /** Login / splash screen – the start destination. */
    object Login : Screen("login")

    object Register : Screen("register")

    /** Home screen shown after a successful login. */
    object Home : Screen("home")

    /** Form to report a lost item. */
    object ReportLost : Screen("report_lost")

    /** Form to report a found item. */
    object ReportFound : Screen("report_found")

    /** Scrollable list of all reported items. */
    object ItemList : Screen("item_list")

    /**
     * Detail screen for a single item.
     *
     * The item ID is passed as a path segment:
     *   navigate to:  "item_detail/l1"
     *   argument key: "itemId"
     */
    object ItemDetail : Screen("item_detail/{itemId}") {
        const val ARG_ITEM_ID = "itemId"

        /** Helper to build the actual navigation route with a concrete ID. */
        fun createRoute(itemId: String) = "item_detail/$itemId"
    }

    /** Admin-only dashboard (stub – extend as needed). */
    object AdminDashboard : Screen("admin_dashboard")
}
