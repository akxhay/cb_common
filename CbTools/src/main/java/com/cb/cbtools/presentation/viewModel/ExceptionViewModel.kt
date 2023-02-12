package com.cb.cbtools.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cb.cbtools.data.model.ExceptionRecord
import com.cb.cbtools.data.repository.ExceptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExceptionViewModel @Inject constructor(val repository: ExceptionRepository) : ViewModel() {

    val exceptions: LiveData<List<ExceptionRecord>>
        get() =
            repository.getException().flowOn(Dispatchers.IO)
                .asLiveData(context = viewModelScope.coroutineContext)


    fun deleteRecord(
    ) {
        viewModelScope.launch {
            repository.deleteRecords()
        }
    }
}
