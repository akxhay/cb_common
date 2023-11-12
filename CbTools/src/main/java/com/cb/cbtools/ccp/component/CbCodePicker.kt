package com.cb.cbtools.ccp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cb.cbtools.ccp.data.CountryData
import com.cb.cbtools.ccp.data.utils.getCountryName
import com.cb.cbtools.ccp.data.utils.getFlags
import com.cb.cbtools.ccp.data.utils.getLibCountries
import com.cb.cbtools.ccp.utils.searchCountry
import com.cb.cbtools.presentation.common.CbTextInputBasic
import java.util.Locale


@Composable
fun CountryCodeDialog(
    modifier: Modifier = Modifier,
    padding: Dp = 15.dp,
    defaultSelectedCountry: CountryData = getLibCountries.first(),
    showCountryCode: Boolean = true,
    pickedCountry: (CountryData) -> Unit,
    showFlag: Boolean = true,
    showCountryName: Boolean = true,
    alertContentColor: Color = MaterialTheme.colorScheme.onSurface,
    alertBackgroundColor: Color = MaterialTheme.colorScheme.surface

) {
    val context = LocalContext.current

    val countryList: List<CountryData> = getLibCountries
    var isPickCountry by remember {
        mutableStateOf(defaultSelectedCountry)
    }
    var isOpenDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .padding(padding)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                isOpenDialog = true
            }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showFlag) {
                Image(
                    modifier = modifier
                        .width(34.dp)
                        .padding(horizontal = 5.dp),
                    painter = painterResource(
                        id = getFlags(
                            isPickCountry.countryCode
                        )
                    ), contentDescription = null
                )
            }
            if (showCountryName) {
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .padding(horizontal = 5.dp),
                    text = isPickCountry.countryCode.uppercase(Locale.ENGLISH),
                    style = MaterialTheme.typography.bodyLarge,
                    color = alertContentColor,
                )
            }
            if (showCountryCode) {
                Text(
                    modifier = Modifier.padding(start = 6.dp),

                    text = isPickCountry.countryPhoneCode,
                    style = MaterialTheme.typography.bodyLarge,
                    color = alertContentColor,
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }

        }


        if (isOpenDialog) {
            CountryDialog(
                countryList = countryList,
                onDismissRequest = { isOpenDialog = false },
                dialogStatus = isOpenDialog,
                onSelected = { countryItem ->
                    pickedCountry(countryItem)
                    isPickCountry = countryItem
                    isOpenDialog = false
                },
                alertContentColor = alertContentColor,
                alertBackgroundColor = alertBackgroundColor
            )
        }
    }
}

@Composable
fun CountryDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryData>,
    onDismissRequest: () -> Unit,
    onSelected: (item: CountryData) -> Unit,
    dialogStatus: Boolean,
    alertContentColor: Color = MaterialTheme.colorScheme.onSurface,
    alertBackgroundColor: Color = MaterialTheme.colorScheme.surface
) {
    val context = LocalContext.current

    var searchValue by remember { mutableStateOf("") }
    if (!dialogStatus) searchValue = ""

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest,
        content = {
            Surface(
                color = alertBackgroundColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Scaffold { scaffold ->
                    scaffold.calculateBottomPadding()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                    ) {
                        CbTextInputBasic(
                            value = searchValue, onValueChange = { searchValue = it },
                            isSearchbar = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn {
                            items(
                                if (searchValue.isEmpty()) countryList else countryList.searchCountry(
                                    searchValue,
                                    context
                                )
                            ) { countryItem ->
                                Row(
                                    Modifier
                                        .padding(18.dp)
                                        .fillMaxWidth()
                                        .clickable(onClick = { onSelected(countryItem) }),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        modifier = modifier.width(30.dp),
                                        painter = painterResource(
                                            id = getFlags(
                                                countryItem.countryCode
                                            )
                                        ), contentDescription = null
                                    )
                                    Text(
                                        modifier = Modifier.padding(horizontal = 18.dp),
                                        text = "("+countryItem.countryPhoneCode + ") " +
                                                stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                        color = alertContentColor,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
    )
}

@Preview
@Composable
fun previewCountryCodeDIalog() {
    CountryCodeDialog(
        pickedCountry = {
            println(it)
        },
        defaultSelectedCountry = getLibCountries.first { it.countryCode =="in" },
        showCountryCode = true,
        showFlag = true,
    )
}


@Preview
@Composable
fun previewCountryDialog() {
    CountryDialog(
        countryList = getLibCountries,
        onDismissRequest = { },
        dialogStatus = true,
        onSelected = { countryItem ->
            print(countryItem)
        }
    )
}
