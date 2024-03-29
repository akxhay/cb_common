package com.cb.cbtools.presentation.common

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cb.cbtools.util.PlayStoreUtil.rate
import kotlinx.coroutines.delay

@Composable
fun RatePopUp(
    activity: Activity,
    dismiss: () -> Unit,
    startColor: Color = MaterialTheme.colorScheme.primary
) {
    CbGenericDialog(
        onDismissClick = {
            dismiss()
        }, onConfirmClick = {
            rate(activity)
        },
        title = "Please rate us on play store",
        text = {
            Column(
                modifier = Modifier.clickable { rate(activity) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "If might not take more than 5 seconds but it means a lot to us.",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                RatingBar(
                    rating = "2.5",
                    starsColor = startColor
                )
            }
        },
        confirmText = "Proceed",
        showDivider = false
    )
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: String = "0.0",
    stars: Int = 5,
    starsColor: Color = Color.Blue,
) {

    val rate = remember {
        mutableStateOf(rating)
    }
    LaunchedEffect(rate) {
        delay(100)
        rate.value = "3.0"
        delay(100)
        rate.value = "3.5"
        delay(100)
        rate.value = "4.0"
        delay(100)
        rate.value = "4.5"
        delay(100)
        rate.value = "5.0"
    }
    val filledStars = kotlin.math.floor(rate.value.toDouble()).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rate.value.toDouble())).toInt()
    val halfStar = !(rate.value.toDouble().rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starsColor
            )
        }
        if (halfStar) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.AutoMirrored.Outlined.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}