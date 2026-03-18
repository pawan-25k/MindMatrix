package com.campus.lostfound.models

import java.util.UUID

/**
 * Represents a user in the system.
 */
data class User(
    val uid: String,
    val email: String,
    val role: String, // "Student" or "Admin"
    val displayName: String? = null
)

/**
 * Represents a lost or found item.
 */
data class Item(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val category: String = "",
    val location: String = "",
    val date: String = "",
    val imageUrl: String? = null,
    val reporterContact: String = "",
    val reporterUid: String = "",
    val type: ItemType = ItemType.LOST,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ItemType {
    LOST, FOUND
}
