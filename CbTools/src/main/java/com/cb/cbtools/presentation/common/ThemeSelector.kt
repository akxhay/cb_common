package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.cbtools.constants.ActionType
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
    currentDynamicColor: Boolean = false,
    updateTheme: (Int, Boolean) -> Unit,
    dismiss: () -> Unit,
) {
    var theme by remember { mutableIntStateOf(currentTheme) }
    var dynamicColor by remember { mutableStateOf(currentDynamicColor) }

    CbGenericDialog(
        onDismissClick = {
            dismiss()
        }, onConfirmClick = {
            updateTheme(theme, dynamicColor)
            dismiss()
        },
        title = title,
        text = {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    CbListItem(
                        titleUnit = {
                            CbListItemTitle(
                                text = "Use dynamic color",
                            )
                        },
                        actionUnit = {
                            CbListItemAction(
                                actionType = ActionType.CHECKBOX,
                                state = dynamicColor
                            ) {
                                dynamicColor = !dynamicColor
                            }
                        },
                        onClick = {
                            dynamicColor = !dynamicColor
                        }
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                ThemeSelector(
                    selectedOption = theme,
                    radioOptions = radioOptions,
                ) {
                    theme = it
                }
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
            .fillMaxWidth(),
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
        updateTheme = { a, b ->

        }
    ) {}


}


