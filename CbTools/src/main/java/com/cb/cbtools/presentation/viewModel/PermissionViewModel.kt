package com.cb.cbtools.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cb.cbtools.dto.CbPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PermissionViewModel @Inject constructor() : ViewModel() {

    private var _currentPermissionState = MutableLiveData(null as CbPermission?)
    var currentPermission: LiveData<CbPermission?> = _currentPermissionState

    fun changeType(permission: CbPermission?) {
        _currentPermissionState.value = permission
    }

}
