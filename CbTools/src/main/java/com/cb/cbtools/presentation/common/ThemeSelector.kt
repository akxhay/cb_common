package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.cbtools.constants.enums.ThemeType

@Composable
fun ThemePopUp(
    title: String = "Select application theme",
    confirmText: String = "Change theme",
    radioOptions: List<String> = listOf(
        ThemeType.SYSTEM.type,
        ThemeType.DARK.type,
        ThemeType.LIGHT.type,
    ),
    currentTheme: Int = 0,
    updateTheme: (Int) -> Unit,
    dismiss: () -> Unit,
) {
    val theme: MutableState<Int> = remember { mutableIntStateOf(currentTheme) }
    CbGenericDialog(
        onDismissClick = {
            dismiss()
        }, onConfirmClick = {
            updateTheme(theme.value)
            dismiss()
        },
        title = title,
        text = {
            ThemeSelector(
                selectedOption = theme.value,
                radioOptions = radioOptions,
            ) {
                theme.value = it
            }
        },
        confirmText = confirmText,
        showDivider = false
    )

}

@Composable
fun ThemeSelector(
    radioOptions: List<String>,
    selectedOption: Int,
    onChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {

        CbRadioGroup(
            selectedOption,
            radioOptions,
            horizontal = false
        ) {
            onChange(radioOptions.indexOf(it))
        }
    }
}


@Composable
@Preview
fun ThemeSelectorPreview() {
    ThemePopUp(
        updateTheme = {}
    ) {}


}


