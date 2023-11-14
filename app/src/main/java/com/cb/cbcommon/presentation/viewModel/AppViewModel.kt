@file:OptIn(FlowPreview::class)

package com.cb.cbcommon.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cb.cbcommon.BaseApplication
import com.cb.cbtools.dto.AppSearchModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    private val isSearching = _isSearching.asStateFlow()

    private val _checkedApp = MutableStateFlow<Set<String>>(emptySet())
    val checkedApp = _checkedApp.asStateFlow()


    private val selectedApps = MutableStateFlow(
        BaseApplication.getInstance().appInfoService.getAppInfoListWithoutIconAndVersion(
            emptySet()
        )
    )


    private val matchedApps = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(selectedApps) { text, persons ->
            if (text.isBlank()) {
                persons
            } else {
                delay(500L)
                persons.filter { x ->
                    x.pkg.contains(text, true) ||
                            x.name.contains(text, true)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            selectedApps.value
        )

    val appSearchModelState = combine(
        searchText,
        matchedApps,
        isSearching
    ) { text, apps, progressBar ->

        AppSearchModelState(
            text,
            apps,
            progressBar
        )
    }

    fun updateApp(app: String) {
        _checkedApp.value = if (_checkedApp.value.contains(app)) {
            _checkedApp.value - app
        } else {
            _checkedApp.value + app
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onClearClick() {
        _isSearching.update { true }
        onSearchTextChange("")
    }
}

