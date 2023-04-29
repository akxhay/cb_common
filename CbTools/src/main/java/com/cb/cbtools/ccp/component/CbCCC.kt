package com.cb.cbtools.ccp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.cb.cbtools.ccp.data.utils.getLibCountries
import com.cb.cbtools.presentation.common.CbTextInputWithError

@Composable
fun CbCCC(
    phoneNumber: MutableState<String>,
    onValueChange: (String) -> Unit,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    onClearClick: () -> Unit,
    error: MutableState<String?>,
    phoneCode: MutableState<String>,
    defaultLang: MutableState<String>
) {

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Column {
                    CountryCodeDialog(
                        pickedCountry = {
                            phoneCode.value = it.countryPhoneCode
                            defaultLang.value = it.countryCode
                        },
                        defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang.value },
                        showCountryCode = showCountryCode,
                        showFlag = showCountryFlag,
                    )
                }
            }
            CbTextInputWithError(
                error = error,
                label = "Enter number",
                onValueChange = onValueChange,
                input = phoneNumber,
                onClearClick = onClearClick,
                keyboardType = KeyboardType.Number,
            )
        }
    }
}
