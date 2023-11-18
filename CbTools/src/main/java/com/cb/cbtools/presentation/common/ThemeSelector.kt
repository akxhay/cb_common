package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cb.cbtools.constants.enums.ThemeType


@Composable
fun ThemeSelector(
    radioOptions: List<String> = listOf(
        ThemeType.SYSTEM.type,
        ThemeType.DARK.type,
        ThemeType.LIGHT.type,
    ),
    selectedOption: Int = 0,
    onChange: (ThemeType) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        CbRadioGroup(
            selectedOption,
            radioOptions,
            horizontal = false
        ) {
            onChange(ThemeType.valueOf(it))
        }
    }
}


@Composable
@Preview
fun ThemeSelectorPreview() {
    var sliderValue by remember { mutableIntStateOf(500) }

    ThemeSelector(

    ) {}


}


