package com.cb.cbtools.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.cb.cbtools.composables.chat.*
import com.cb.cbtools.dynamic.DynamicConfig


@Composable
fun SentMessageRow(
    drawArrow: Boolean = true,
    text: String,
    messageTime: String,
    extraText: String = "",
    extra: Boolean = false,
    dynamicConfig: DynamicConfig
) {
    Column(
        modifier = Modifier
            .padding(start = 60.dp, end = 8.dp, top = if (drawArrow) 2.dp else 0.dp, bottom = 2.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.End
    ) {

        Column(
            Modifier.drawBubbleWithShape(
                bubbleState = rememberBubbleState(
                    backgroundColor = dynamicConfig.getSentBubbleColor(),
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
                color = dynamicConfig.getPrimaryTextOnSentBubbleColor(),
                messageStat = {
                    MessageTimeText(
                        modifier = Modifier.wrapContentSize(),
                        messageTime = messageTime,
                        color = dynamicConfig.getSecondaryTextOnSentBubbleColor()
                    )
                },
                urlColor = dynamicConfig.getUrlTextColor()
            )
        }
        if (extra) {
            Text(
                text = extraText,
                style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                color = dynamicConfig.getPrimaryTextOnBackGroundColor()
            )
        }
    }
}

@Composable
fun ReceivedMessageRow(
    drawArrow: Boolean = true,
    text: String,
    messageTime: String,
    dynamicConfig: DynamicConfig
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
                    backgroundColor =dynamicConfig.getReceivedBubbleColor(),
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
                color = dynamicConfig.getPrimaryTextOnReceivedBubbleColor(),
                urlColor = dynamicConfig.getUrlTextColor(),
                messageStat = {
                    MessageTimeText(
                        modifier = Modifier.wrapContentSize(),
                        messageTime = messageTime,
                        color = dynamicConfig.getSecondaryTextOnReceivedBubbleColor()
                    )
                }
            )
        }
    }
}


@Composable
fun DayHeader(dayString: String, dynamicConfig: DynamicConfig) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .height(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = dynamicConfig.getDateBubbleColor(),
            ),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = dayString,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp),
                style = MaterialTheme.typography.labelLarge,
                color = dynamicConfig.getPrimaryTextOnDateBubbleColor()
            )
        }

    }
}

