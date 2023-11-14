package com.cb.cbtools.ccp.component

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.cb.cbtools.R
import com.cb.cbtools.ccp.data.utils.checkPhoneNumber
import com.cb.cbtools.ccp.data.utils.getDefaultLangCode
import com.cb.cbtools.ccp.data.utils.getDefaultPhoneCode
import com.cb.cbtools.presentation.common.CbGenericDialog

@Composable
fun ShowCCDialog(
    title: String = "Send message without saving number",
    dismissDialog: () -> Unit,
    onProceed: (String, String) -> Unit
) {
    val context = LocalContext.current
    val phoneNumber = rememberSaveable { mutableStateOf("") }

    val phoneCode = rememberSaveable {
        mutableStateOf(
            getDefaultPhoneCode(
                context
            )
        )
    }
    val defaultLang = rememberSaveable {
        mutableStateOf(
            getDefaultLangCode(context)
        )
    }
    val validationMessage = rememberSaveable {
        mutableStateOf("")
    }
    val onInputChanged: (String) -> Unit = {
        phoneNumber.value = it
    }
    val validation: (String) -> Boolean = {
        if (checkPhoneNumber(
                phone = phoneNumber.value,
                fullPhoneNumber = phoneCode.value + phoneNumber.value,
                countryCode = defaultLang.value
            )
        ) {
            validationMessage.value = "Please enter valid number"
            false
        } else {
            true
        }
    }
    val onClearClick: () -> Unit = {
        onInputChanged("")
    }

    CbGenericDialog(
        onConfirmClick = {
            if (validation(phoneNumber.value)) {
                onProceed(phoneCode.value, phoneNumber.value)
            } else {
                Toast.makeText(context, validationMessage.value, Toast.LENGTH_SHORT).show()
            }
        },
        onDismissClick = {
            dismissDialog()
        },
        title = title,
        text = {
            CbCCC(
                phoneNumber = phoneNumber.value,
                defaultLang = defaultLang.value,
                onInputChanged = onInputChanged,
                onClearClick = onClearClick,
                validation = validation,
                validationMessage = validationMessage.value,
                onPickedCountryChange = {
                    phoneCode.value = it.countryPhoneCode
                    defaultLang.value = it.countryCode
                }
            )

        },
        confirmText = stringResource(R.string.proceed),
    )
}
