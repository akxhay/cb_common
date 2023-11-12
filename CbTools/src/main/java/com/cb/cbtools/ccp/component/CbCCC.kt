package com.cb.cbtools.ccp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.cb.cbtools.ccp.data.CountryData
import com.cb.cbtools.ccp.data.utils.getLibCountries
import com.cb.cbtools.presentation.common.CbTextInputWithError

@Composable
fun CbCCC(
    phoneNumber: String,
    defaultLang: String,
    validation: (String) -> Boolean,
    validationMessage: String,
    onInputChanged: (String) -> Unit,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
    onClearClick: () -> Unit,
    onPickedCountryChange: (CountryData) -> Unit,
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
                            onPickedCountryChange(it)
                        },
                        defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                        showCountryCode = showCountryCode,
                        showFlag = showCountryFlag,
                    )
                }
            }
            CbTextInputWithError(
                input = phoneNumber,
                onInputChanged = onInputChanged,
                validation = validation,
                validationMessage = validationMessage,
                label = "Enter number",
                onClearClick = onClearClick
            )
        }
    }
}

@Preview
@Composable
fun previewCbCCC() {
    CbCCC(
        phoneNumber = "3212312",
        defaultLang = "in",
        validation = { true },
        validationMessage = "Invalid input",
        onInputChanged = {},
        onClearClick = {},
        onPickedCountryChange = {}
    )
}
