package com.cb.cbcpp.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cb.cbcpp.presentation.data.utils.checkPhoneNumber
import com.cb.cbcpp.presentation.data.utils.getDefaultLangCode
import com.cb.cbcpp.presentation.data.utils.getDefaultPhoneCode
import com.cb.cbcpp.presentation.data.utils.getLibCountries


private var fullNumberState: String by mutableStateOf("")
private var checkNumberState: Boolean by mutableStateOf(false)
private var phoneNumberState: String by mutableStateOf("")
private var countryCodeState: String by mutableStateOf("")


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CbCCC(
    text: String,
    onValueChange: (String) -> Unit,
    showCountryCode: Boolean = true,
    showCountryFlag: Boolean = true,
) {
    val context = LocalContext.current
    var textFieldValue by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showClearButton by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var phoneCode by rememberSaveable {
        mutableStateOf(
            getDefaultPhoneCode(
                context
            )
        )
    }
    var defaultLang by rememberSaveable {
        mutableStateOf(
            getDefaultLangCode(context)
        )
    }

    fullNumberState = phoneCode + textFieldValue
    phoneNumberState = textFieldValue
    countryCodeState = defaultLang


    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row {
                Column {
                    TogiCodeDialog(
                        pickedCountry = {
                            phoneCode = it.countryPhoneCode
                            defaultLang = it.countryCode
                        },
                        defaultSelectedCountry = getLibCountries.single { it.countryCode == defaultLang },
                        showCountryCode = showCountryCode,
                        showFlag = showCountryFlag
                    )
                }
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .onFocusChanged { focusState ->
                        showClearButton = (focusState.isFocused)
                    }
                    .focusRequester(focusRequester)
                    .drawBehind {
                        val strokeWidth = Stroke.DefaultMiter
                        val y = size.height - strokeWidth / 2

                        drawLine(
                            Color.LightGray,
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                    },
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    if (text != it) {
                        onValueChange(it)
                    }
                },
                placeholder = { Text(text = "Enter number") },

                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.Transparent,
                    cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                ),
                trailingIcon = {
                    AnimatedVisibility(
                            visible = textFieldValue.isNotEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            IconButton(onClick = {
                                textFieldValue = ""
                                onValueChange("")
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = ""
                                )
                            }

                    }
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
            )
        }
    }
}

fun getFullPhoneNumber(): String {
    return fullNumberState
}

fun getOnlyPhoneNumber(): String {
    return phoneNumberState
}

fun getErrorStatus(): Boolean {
    return checkNumberState
}

fun isPhoneNumber(): Boolean {
    val check = checkPhoneNumber(
        phone = phoneNumberState, fullPhoneNumber = fullNumberState, countryCode = countryCodeState
    )
    checkNumberState = check
    return check
}