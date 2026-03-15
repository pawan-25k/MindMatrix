package com.mindmatrix.lostfound.data

import com.mindmatrix.lostfound.model.FoundItem
import com.mindmatrix.lostfound.model.ItemSummary
import com.mindmatrix.lostfound.model.LostItem
import com.mindmatrix.lostfound.model.toSummary

/**
 * LostFoundRepository – abstraction layer between the UI and the data source.
 *
 * Currently backed by [SampleData]. To switch to Firebase Firestore:
 *   1. Inject a FirebaseFirestore instance.
 *   2. Replace body of each function with the appropriate Firestore call.
 *   3. Consider making these functions `suspend` for coroutine support.
 *
 * The ViewModels depend on this interface, not on SampleData directly,
 * so backend integration will NOT require changes to any screen composables.
 */
class LostFoundRepository {

    /** Returns all items (lost + found) merged and sorted by date. */
    fun getAllItems(): List<ItemSummary> = SampleData.allItems

    /** Returns only the lost items list. */
    fun getLostItems(): List<LostItem> = SampleData.lostItems

    /** Returns only the found items list. */
    fun getFoundItems(): List<FoundItem> = SampleData.foundItems

    /** Finds a single item by its ID across both lost and found lists. */
    fun getItemById(id: String): ItemSummary? = SampleData.allItems.find { it.id == id }

    /**
     * Submits a new lost item report.
     *
     * TODO: Replace with Firestore .add() or Retrofit POST when backend is ready.
     */
    fun reportLostItem(item: LostItem): Boolean {
        // Placeholder – in a real app, persist to Firestore / Room / API
        return true
    }

    /**
     * Submits a new found item report.
     *
     * TODO: Replace with Firestore .add() or Retrofit POST when backend is ready.
     */
    fun reportFoundItem(item: FoundItem): Boolean {
        // Placeholder – in a real app, persist to Firestore / Room / API
        return true
    }

    /**
     * Filters items by a search query (name or location, case-insensitive).
     */
    fun searchItems(query: String): List<ItemSummary> {
        if (query.isBlank()) return SampleData.allItems
        val q = query.lowercase()
        return SampleData.allItems.filter {
            it.name.lowercase().contains(q) || it.location.lowercase().contains(q)
        }
    }
}
