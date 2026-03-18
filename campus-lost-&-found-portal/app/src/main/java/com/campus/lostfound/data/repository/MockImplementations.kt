package com.campus.lostfound.data.repository

import com.campus.lostfound.models.Item
import com.campus.lostfound.models.ItemType
import com.campus.lostfound.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class MockAuthRepository : AuthRepository {
    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: Flow<User?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String, role: String): Result<User> {
        delay(1000) // Simulate network delay
        val user = User(uid = "mock_uid", email = email, role = role, displayName = "Mock User")
        _currentUser.value = user
        return Result.success(user)
    }

    override suspend fun logout() {
        _currentUser.value = null
    }
}

class MockItemRepository : ItemRepository {
    private val _items = MutableStateFlow<List<Item>>(
        listOf(
            Item("1", "Blue Backpack", "Contains textbooks.", "Bags", "Library", "2023-10-25", type = ItemType.LOST, reporterContact = "student1@univ.edu"),
            Item("2", "iPhone 13", "Found near coffee.", "Electronics", "Cafeteria", "2023-10-26", type = ItemType.FOUND, reporterContact = "admin@univ.edu")
        )
    )
    override val allItems: Flow<List<Item>> = _items.asStateFlow()

    override suspend fun reportItem(item: Item): Result<Unit> {
        delay(500)
        val currentList = _items.value.toMutableList()
        currentList.add(0, item)
        _items.value = currentList
        return Result.success(Unit)
    }

    override suspend fun getItemById(id: String): Item? {
        return _items.value.find { it.id == id }
    }
}
