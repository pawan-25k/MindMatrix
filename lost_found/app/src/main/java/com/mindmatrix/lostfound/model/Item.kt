package com.mindmatrix.lostfound.model

/**
 * Categorises reported items.
 * Add more categories as required without changing any screen logic.
 */
enum class ItemCategory(val label: String) {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    ACCESSORIES("Accessories"),
    DOCUMENTS("Documents"),
    BOOKS("Books & Stationery"),
    KEYS("Keys"),
    WALLET("Wallet / Purse"),
    OTHER("Other");

    override fun toString() = label
}

/**
 * Represents a Lost Item submitted by a student.
 *
 * All fields with defaults make it easy to create instances incrementally
 * (e.g. inside a ViewModel) before the form is submitted.
 *
 * @property id          Unique identifier – use a UUID or Firestore document ID.
 * @property name        Short display name of the item.
 * @property description Detailed description to help identify the item.
 * @property category    Category chosen from [ItemCategory].
 * @property location    Where the item was last seen / lost.
 * @property dateLost    Formatted date string (ISO-8601 recommended, e.g. "2026-03-10").
 * @property imageUrl    Remote or local URI for the item image; null if not provided.
 * @property contactEmail Email of the student who reported the lost item.
 * @property contactPhone Optional phone number.
 * @property reportedBy  Display name of the reporter.
 */
data class LostItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: ItemCategory = ItemCategory.OTHER,
    val location: String = "",
    val dateLost: String = "",
    val imageUrl: String? = null,
    val contactEmail: String = "",
    val contactPhone: String = "",
    val reportedBy: String = ""
)

/**
 * Represents a Found Item submitted by a student.
 *
 * Structurally similar to [LostItem] but semantically distinct –
 * kept as a separate class so backend collections/tables remain decoupled.
 *
 * @property id            Unique identifier.
 * @property name          Short display name of the item.
 * @property description   Detailed description.
 * @property category      Category chosen from [ItemCategory].
 * @property location      Where the item was found.
 * @property dateFound     Formatted date string (ISO-8601).
 * @property imageUrl      Optional image URI.
 * @property contactEmail  Email of the student who found the item.
 * @property contactPhone  Optional phone number.
 * @property reportedBy    Display name of the finder.
 * @property handedToAdmin Whether the item has been handed to campus administration.
 */
data class FoundItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: ItemCategory = ItemCategory.OTHER,
    val location: String = "",
    val dateFound: String = "",
    val imageUrl: String? = null,
    val contactEmail: String = "",
    val contactPhone: String = "",
    val reportedBy: String = "",
    val handedToAdmin: Boolean = false
)

/**
 * Merged view model used by the Item List and Item Detail screens.
 * This allows a single [LazyColumn] to show both Lost and Found items together.
 *
 * @property id          Item ID (matches source record).
 * @property name        Item display name.
 * @property category    Category label.
 * @property location    Where the item was lost/found.
 * @property date        Date string (lost or found date).
 * @property imageUrl    Optional image URI.
 * @property description Full description.
 * @property contactEmail  Reporter's email.
 * @property contactPhone  Reporter's phone (optional).
 * @property reportedBy    Reporter's display name.
 * @property isLost      True if this is a Lost item; false means Found item.
 */
data class ItemSummary(
    val id: String,
    val name: String,
    val category: ItemCategory,
    val location: String,
    val date: String,
    val imageUrl: String?,
    val description: String,
    val contactEmail: String,
    val contactPhone: String,
    val reportedBy: String,
    val isLost: Boolean
)

// ---------------------------------------------------------------------------
// Extension helpers to unify LostItem / FoundItem → ItemSummary
// ---------------------------------------------------------------------------

fun LostItem.toSummary() = ItemSummary(
    id = id, name = name, category = category, location = location,
    date = dateLost, imageUrl = imageUrl, description = description,
    contactEmail = contactEmail, contactPhone = contactPhone,
    reportedBy = reportedBy, isLost = true
)

fun FoundItem.toSummary() = ItemSummary(
    id = id, name = name, category = category, location = location,
    date = dateFound, imageUrl = imageUrl, description = description,
    contactEmail = contactEmail, contactPhone = contactPhone,
    reportedBy = reportedBy, isLost = false
)
