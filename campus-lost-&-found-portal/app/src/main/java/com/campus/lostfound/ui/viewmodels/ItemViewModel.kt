package com.campus.lostfound.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.lostfound.data.repository.ItemRepository
import com.campus.lostfound.models.Item
import com.campus.lostfound.models.ItemType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    val items: StateFlow<List<Item>> = repository.allItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _reportState = MutableStateFlow<ReportState>(ReportState.Idle)
    val reportState: StateFlow<ReportState> = _reportState.asStateFlow()

    fun reportItem(
        name: String,
        description: String,
        category: String,
        location: String,
        date: String,
        type: ItemType,
        contact: String,
        uid: String
    ) {
        viewModelScope.launch {
            _reportState.value = ReportState.Loading
            val newItem = Item(
                name = name,
                description = description,
                category = category,
                location = location,
                date = date,
                type = type,
                reporterContact = contact,
                reporterUid = uid
            )
            val result = repository.reportItem(newItem)
            result.onSuccess {
                _reportState.value = ReportState.Success
            }.onFailure {
                _reportState.value = ReportState.Error(it.message ?: "Failed to report item")
            }
        }
    }

    fun resetReportState() {
        _reportState.value = ReportState.Idle
    }
}

sealed class ReportState {
    object Idle : ReportState()
    object Loading : ReportState()
    object Success : ReportState()
    data class Error(val message: String) : ReportState()
}
