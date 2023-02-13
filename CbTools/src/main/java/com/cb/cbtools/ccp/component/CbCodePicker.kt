package com.cb.cbtools.ccp.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cb.cbtools.ccp.data.CountryData
import com.cb.cbtools.ccp.data.utils.getCountryName
import com.cb.cbtools.ccp.data.utils.getFlags
import com.cb.cbtools.ccp.data.utils.getLibCountries
import com.cb.cbtools.ccp.utils.searchCountry
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.presentation.common.CbTextInputBasic
import java.util.*


@Composable
fun CountryCodeDialog(
    modifier: Modifier = Modifier,
    padding: Dp = 15.dp,
    defaultSelectedCountry: CountryData = getLibCountries.first(),
    showCountryCode: Boolean = true,
    pickedCountry: (CountryData) -> Unit,
    showFlag: Boolean = true,
    showCountryName: Boolean = true,
    dynamicConfig: DynamicConfig
    ) {
    val context = LocalContext.current

    val countryList: List<CountryData> = getLibCountries
    var isPickCountry by remember {
        mutableStateOf(defaultSelectedCountry)
    }
    var isOpenDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier
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
                    color = dynamicConfig.getAlertContentColor(),
                )
            }
            if (showCountryCode) {
                Text(
                    modifier = Modifier.padding(start = 6.dp),

                    text = isPickCountry.countryPhoneCode,
                    style = MaterialTheme.typography.bodyLarge,
                    color = dynamicConfig.getAlertContentColor(),
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }

        }


        if (isOpenDialog) {
            CountryDialog(
                countryList = countryList,
                onDismissRequest = { isOpenDialog = false },
                context = context,
                dialogStatus = isOpenDialog,
                onSelected = { countryItem ->
                    pickedCountry(countryItem)
                    isPickCountry = countryItem
                    isOpenDialog = false
                },
                dynamicConfig = dynamicConfig
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CountryDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryData>,
    onDismissRequest: () -> Unit,
    onSelected: (item: CountryData) -> Unit,
    context: Context,
    dialogStatus: Boolean,
    dynamicConfig: DynamicConfig
) {
    var searchValue by remember { mutableStateOf("") }
    if (!dialogStatus) searchValue = ""

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest,
        content = {
            Surface(
                color = dynamicConfig.getAlertBackgroundColor(),
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
                            dynamicConfig = dynamicConfig,
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

                                        text = countryItem.countryPhoneCode + " " +
                                                stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                        color = dynamicConfig.getAlertContentColor(),
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

