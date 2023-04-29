package com.cb.cbtools.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.cb.cbtools.presentation.common.chat.*


@Composable
fun SentMessageRow(
    drawArrow: Boolean = true,
    text: String,
    messageTime: String,
    extraText: String = "",
    extra: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    primaryTextColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    secondaryTextColor: Color = MaterialTheme.colorScheme.scrim,
    urlColor: Color = MaterialTheme.colorScheme.outline,
    messageColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Column(
        modifier = Modifier
            .padding(start = 60.dp, end = 8.dp, top = if (drawArrow) 5.dp else 0.dp, bottom = 2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.End
    ) {

        Column(
            Modifier.drawBubbleWithShape(
                bubbleState = rememberBubbleState(
                    backgroundColor = backgroundColor,
                    alignment = ArrowAlignment.RightTop,
                    cornerRadius = 12.dp,
                    drawArrow = drawArrow,
                    shadow = BubbleShadow(elevation = 1.dp),
                    clickable = true
                )
            )
        ) {
            ChatFlexBoxLayout(
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                text = text,
                color = primaryTextColor,
                messageStat = {
                    MessageTimeText(
                        modifier = Modifier.wrapContentSize(),
                        messageTime = messageTime,
                        color = secondaryTextColor
                    )
                },
                urlColor = urlColor
            )
        }
        if (extra) {
            Text(
                text = extraText,
                style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                color = messageColor
            )
        }
    }
}

@Composable
fun ReceivedMessageRow(
    drawArrow: Boolean = true,
    text: String,
    messageTime: String,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    primaryTextColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    secondaryTextColor: Color = MaterialTheme.colorScheme.scrim,
    urlColor: Color = MaterialTheme.colorScheme.outline
) {

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 60.dp, top = if (drawArrow) 2.dp else 0.dp, bottom = 2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            Modifier.drawBubbleWithShape(
                bubbleState = rememberBubbleState(
                    backgroundColor = backgroundColor,
                    alignment = ArrowAlignment.LeftTop,
                    drawArrow = drawArrow,
                    cornerRadius = 12.dp,
                    shadow = BubbleShadow(elevation = 1.dp),
                    clickable = true
                )
            )
        ) {
            ChatFlexBoxLayout(
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                text = text,
                color = primaryTextColor,
                urlColor = urlColor,
                messageStat = {
                    MessageTimeText(
                        modifier = Modifier.wrapContentSize(),
                        messageTime = messageTime,
                        color = secondaryTextColor
                    )
                }
            )
        }
    }
}


@Composable
fun DayHeader(
    dayString: String,
    dateBubbleColor: Color = MaterialTheme.colorScheme.primaryContainer,
    primaryTextOnDateBubbleColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .height(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = dateBubbleColor,
            ),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = dayString,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp),
                style = MaterialTheme.typography.labelLarge,
                color = primaryTextOnDateBubbleColor
            )
        }

    }
}

