package com.campus.lostfound.data.repository

import com.campus.lostfound.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface for Authentication operations.
 * Easy to swap with FirebaseAuth later.
 */
interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, password: String, role: String): Result<User>
    suspend fun logout()
}

/**
 * Interface for Item operations.
 * Easy to swap with Firestore later.
 */
interface ItemRepository {
    val allItems: Flow<List<com.campus.lostfound.models.Item>>
    suspend fun reportItem(item: com.campus.lostfound.models.Item): Result<Unit>
    suspend fun getItemById(id: String): com.campus.lostfound.models.Item?
}
