package com.cb.cbtools.presentation.common.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cbtools.presentation.common.IndicatingIconButton

val circleButtonSize = 44.dp

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    message: String = "Message",
    onMessageChange: (String) -> Unit,
    fabContainerColor: Color = MaterialTheme.colorScheme.primary,
    fabContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {

    var input by remember { mutableStateOf(TextFieldValue("")) }
    val textEmpty: Boolean by derivedStateOf { input.text.isEmpty() }

    Row(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        ChatTextField(
            modifier = modifier.weight(1f),
            input = input,
            empty = textEmpty,
            enabled = enabled,
            message = message,
            onValueChange = {
                input = it
            },
            containerColor = containerColor,
            contentColor = contentColor

        )

        Spacer(modifier = Modifier.width(6.dp))

        FloatingActionButton(
            modifier = Modifier.size(48.dp),
            backgroundColor = fabContainerColor,
            onClick = {
                if (!textEmpty) {
                    onMessageChange(input.text)
                    input = TextFieldValue("")
                }
            }
        ) {
            Icon(
                tint = fabContentColor,
                imageVector = Icons.Filled.Send,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ChatTextField(
    modifier: Modifier = Modifier,
    input: TextFieldValue,
    empty: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean,
    message: String,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = containerColor,
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                IndicatingIconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.then(Modifier.size(circleButtonSize)),
                    indication = rememberRipple(bounded = false, radius = circleButtonSize / 2)
                ) {
                    Icon(
                        imageVector = if (enabled) Icons.Default.Mood else Icons.Default.MoodBad,
                        contentDescription = "emoji",
                        tint = contentColor
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = circleButtonSize),
                    contentAlignment = Alignment.CenterStart
                ) {

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textStyle = TextStyle(
                            fontSize = 18.sp
                        ),
                        value = input,
                        enabled = enabled,
                        onValueChange = onValueChange,
                        cursorBrush = SolidColor(
                            contentColor,
                        ),
                        decorationBox = { innerTextField ->
                            if (empty) {
                                Text(
                                    message,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = contentColor
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }
    }
}

