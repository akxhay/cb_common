package com.cb.cbtools.permission.factory

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import com.cb.cbtools.constants.enums.PermissionType
import com.cb.cbtools.permission.PermissionHandler

class ReadWriteContacts : PermissionHandler {
    override fun getArrayOfPermissionAsk() = arrayOf(
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_CONTACTS
    )

    override fun getPermissionType() = PermissionType.PERMISSION_READ_WRITE_CONTACTS

    override fun getPermissionPopUpTitle() = "Provide permissions for accessing contacts"

    override fun getPermissionPopUpText() =
        "Contact access is important for this app. Please grant the permission."

    override fun getPermissionButtonText() = "Provide access to Contacts"

    override fun getPermissionIcon() = Icons.Default.Contacts


}