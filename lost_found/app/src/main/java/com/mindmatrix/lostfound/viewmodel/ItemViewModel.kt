package com.mindmatrix.lostfound.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mindmatrix.lostfound.data.LostFoundRepository
import com.mindmatrix.lostfound.model.FoundItem
import com.mindmatrix.lostfound.model.ItemCategory
import com.mindmatrix.lostfound.model.ItemSummary
import com.mindmatrix.lostfound.model.LostItem
import com.mindmatrix.lostfound.model.User
import java.util.UUID

/**
 * ItemViewModel – shared ViewModel for all item-related screens.
 *
 * Responsibilities:
 *  - Provide item data (list, search, single-item lookup) to the UI.
 *  - Manage form state for the Report Lost / Report Found screens.
 *  - Delegate persistence to [LostFoundRepository].
 */
class ItemViewModel : ViewModel() {

    private val repository = LostFoundRepository()

    // ── Item list & search state ─────────────────────────────────────────────

    /** Full item list (lost + found merged). Populated once on creation. */
    var items by mutableStateOf(repository.getAllItems())
        private set

    /** Current search query entered on the Item List screen. */
    var searchQuery by mutableStateOf("")
        private set

    /** Items filtered by [searchQuery]. */
    val filteredItems: List<ItemSummary>
        get() = repository.searchItems(searchQuery)

    fun onSearchQueryChange(query: String) { searchQuery = query }

    /** Returns a single item by ID, or null if not found. */
    fun getItemById(id: String): ItemSummary? = repository.getItemById(id)

    // ── Report Lost Item form state ──────────────────────────────────────────

    var lostItemName        by mutableStateOf("")
    var lostItemDescription by mutableStateOf("")
    var lostItemCategory    by mutableStateOf(ItemCategory.OTHER)
    var lostItemLocation    by mutableStateOf("")
    var lostItemDate        by mutableStateOf("")
    var lostItemImageUri    by mutableStateOf<String?>(null)
    var lostFormError       by mutableStateOf<String?>(null)
    var lostFormSuccess     by mutableStateOf(false)

    /** Validates and submits the Report Lost form. */
    fun submitLostItem(currentUser: User?, onSuccess: () -> Unit) {
        lostFormError = null
        lostFormSuccess = false

        when {
            lostItemName.isBlank()     -> { lostFormError = "Item name is required."; return }
            lostItemLocation.isBlank() -> { lostFormError = "Location is required."; return }
            lostItemDate.isBlank()     -> { lostFormError = "Date lost is required."; return }
        }

        val item = LostItem(
            id           = UUID.randomUUID().toString(),
            name         = lostItemName.trim(),
            description  = lostItemDescription.trim(),
            category     = lostItemCategory,
            location     = lostItemLocation.trim(),
            dateLost     = lostItemDate.trim(),
            imageUrl     = lostItemImageUri,
            contactEmail = currentUser?.email ?: "",
            reportedBy   = currentUser?.displayName ?: "Anonymous"
        )

        val success = repository.reportLostItem(item)
        if (success) {
            lostFormSuccess = true
            resetLostForm()
            onSuccess()
        } else {
            lostFormError = "Failed to submit report. Please try again."
        }
    }

    private fun resetLostForm() {
        lostItemName = ""; lostItemDescription = ""
        lostItemCategory = ItemCategory.OTHER; lostItemLocation = ""
        lostItemDate = ""; lostItemImageUri = null
    }

    // ── Report Found Item form state ─────────────────────────────────────────

    var foundItemName        by mutableStateOf("")
    var foundItemDescription by mutableStateOf("")
    var foundItemCategory    by mutableStateOf(ItemCategory.OTHER)
    var foundItemLocation    by mutableStateOf("")
    var foundItemDate        by mutableStateOf("")
    var foundItemImageUri    by mutableStateOf<String?>(null)
    var foundHandedToAdmin   by mutableStateOf(false)
    var foundFormError       by mutableStateOf<String?>(null)
    var foundFormSuccess     by mutableStateOf(false)

    /** Validates and submits the Report Found form. */
    fun submitFoundItem(currentUser: User?, onSuccess: () -> Unit) {
        foundFormError = null
        foundFormSuccess = false

        when {
            foundItemName.isBlank()     -> { foundFormError = "Item name is required."; return }
            foundItemLocation.isBlank() -> { foundFormError = "Location is required."; return }
            foundItemDate.isBlank()     -> { foundFormError = "Date found is required."; return }
        }

        val item = FoundItem(
            id             = UUID.randomUUID().toString(),
            name           = foundItemName.trim(),
            description    = foundItemDescription.trim(),
            category       = foundItemCategory,
            location       = foundItemLocation.trim(),
            dateFound      = foundItemDate.trim(),
            imageUrl       = foundItemImageUri,
            contactEmail   = currentUser?.email ?: "",
            reportedBy     = currentUser?.displayName ?: "Anonymous",
            handedToAdmin  = foundHandedToAdmin
        )

        val success = repository.reportFoundItem(item)
        if (success) {
            foundFormSuccess = true
            resetFoundForm()
            onSuccess()
        } else {
            foundFormError = "Failed to submit report. Please try again."
        }
    }

    private fun resetFoundForm() {
        foundItemName = ""; foundItemDescription = ""
        foundItemCategory = ItemCategory.OTHER; foundItemLocation = ""
        foundItemDate = ""; foundItemImageUri = null; foundHandedToAdmin = false
    }
}
