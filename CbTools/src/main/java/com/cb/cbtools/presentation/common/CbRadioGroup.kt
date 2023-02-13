package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.Arrangement
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
import com.cb.cbtools.dynamic.DynamicConfig

@Composable
fun CbRadioGroup(
    selectedOption: Int,
    radioOptions: List<String>,
    dynamicConfig: DynamicConfig,
    onOptionSelected: (String) -> Unit,
) {
    CbListItem(
        dynamicConfig = dynamicConfig,
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
                                selectedColor = dynamicConfig.getRadioCheckedColor(),
                                unselectedColor = dynamicConfig.getRadioUnCheckedColor()
                            )
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = dynamicConfig.getPrimaryTextOnBackGroundColor()
                        )
                    }
                }
            }
        }
    )
}