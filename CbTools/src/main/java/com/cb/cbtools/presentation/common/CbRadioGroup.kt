package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CbRadioGroup(
    selectedOption: Int,
    radioOptions: List<String>,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onOptionSelected: (String) -> Unit,

    ) {
    CbListItem(
        titleUnit = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                radioOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (text == radioOptions[selectedOption]),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = (text == radioOptions[selectedOption]),
                            onClick = { onOptionSelected(text) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = selectedColor,
                                unselectedColor = unselectedColor
                            )
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun CbRadioGroupColumn(
    selectedOption: Int,
    radioOptions: List<String>,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onOptionSelected: (String) -> Unit,

    ) {
    CbListItem(
        titleUnit = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                radioOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == radioOptions[selectedOption]),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = (text == radioOptions[selectedOption]),
                            onClick = { onOptionSelected(text) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = selectedColor,
                                unselectedColor = unselectedColor
                            )
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor
                        )
                    }
                }
            }
        }
    )
}