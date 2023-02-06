package com.cb.cbtools.composables.chat

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cb.cb_test.application.util.common.UrlUtil


/**
 * Message to contain chat context. This is wrapper around [Text] with [String] context.
 */
@Composable
fun Message(
    modifier: Modifier = Modifier,
    text: String,
    onTextLayout: (TextLayoutResult) -> Unit,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = 16.sp,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    urlColor: Color,
) {
    if (text.contains("http") || text.contains("www")) {
        val uriHandler = LocalUriHandler.current
        val annotatedLinkString: AnnotatedString = buildAnnotatedString {
            val getStartAndEndOfUrls = UrlUtil.getStartAndEndOfUrls(text)
            append(text)

            for (item in getStartAndEndOfUrls) {
                val startIndex = item[0]
                val endIndex = item[1]
                addStyle(
                    style = SpanStyle(
                        color = urlColor,
                        textDecoration = TextDecoration.Underline
                    ), start = startIndex, end = endIndex
                )
                addStringAnnotation(
                    tag = "URL",
                    annotation = text.substring(startIndex, endIndex),
                    start = startIndex,
                    end = endIndex
                )
            }
        }

        ClickableText(
            modifier = modifier,
            onTextLayout = onTextLayout,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            text = annotatedLinkString,
            onClick = {
                annotatedLinkString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )
    } else {
        Text(
            modifier = modifier,
            text = text,
            onTextLayout = onTextLayout,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
        )
    }
}