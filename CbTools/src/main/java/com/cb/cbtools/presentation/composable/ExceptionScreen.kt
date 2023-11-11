package com.cb.cbtools.presentation.composable

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cb.cbtools.R
import com.cb.cbtools.data.model.ExceptionRecord
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbDecisionDialog
import com.cb.cbtools.presentation.common.CbNoResult
import com.cb.cbtools.presentation.viewModel.ExceptionViewModel
import com.cb.cbtools.util.DateUtil

@Composable
fun ExceptionScreen(
    navController: NavController,
    viewModel: ExceptionViewModel = hiltViewModel()
) {
    val exceptions by viewModel.exceptions.observeAsState(initial = emptyList())
    val showAlert = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CbAppBar(
                title = stringResource(id = R.string.exceptions_title),
                backAction = { navController.navigateUp() },
                actions = {
                    IconButton(onClick = {
                        showAlert.value = true
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            stringResource(id = R.string.delete),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (exceptions.isNotEmpty()) {
                ExceptionLoaded(exceptions)
            } else {
                CbNoResult(
                    text = stringResource(id = R.string.no_exceptions)
                )
            }
        }
        if (showAlert.value) {
            DeleteExceptionAlertDialog(
                LocalContext.current,
                viewModel,
                showAlert,
                navController
            )
        }
    }
}

@Composable
fun DeleteExceptionAlertDialog(
    context: Context,
    viewModel: ExceptionViewModel,
    showAlert: MutableState<Boolean>,
    navController: NavController,
) {
    CbDecisionDialog(
        onConfirmClick = {
            Toast.makeText(context, "Exceptions are being deleted", Toast.LENGTH_SHORT).show()
            viewModel.deleteRecord()
            showAlert.value = false
            navController.navigateUp()
        },
        onDismissClick = {
            showAlert.value = false
        },
        title = stringResource(id = R.string.confirm_delete),
        text = stringResource(id = R.string.delete_all_exceptions),
    )
}

@Composable
fun ExceptionLoaded(
    exceptions: List<ExceptionRecord>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            items(exceptions) { exception ->
                ExceptionItem(exception)
            }
        }
    )
}

@Composable
fun ExceptionItem(
    exception: ExceptionRecord
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column {
            ExceptionDateInfo(exception)
            if (exception.exceptionDetails != null) {
                ExceptionDetails(exception)
            }
            if (exception.stackTrace != null) {
                ExceptionStackTrace(exception)
            }
        }
    }
}

@Composable
fun ExceptionDateInfo(exception: ExceptionRecord) {
    Row(modifier = Modifier.padding(20.dp, 10.dp)) {
        Box(contentAlignment = Center) {
            Text(
                text = "${exception.className!!} : ${exception.lineNumber} : ${
                    DateUtil.convertCompleteDateTime(
                        exception.time
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ExceptionDetails(exception: ExceptionRecord) {
    Row(modifier = Modifier.padding(20.dp, 10.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Center
        ) {
            Text(
                text = exception.exceptionDetails!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun ExceptionStackTrace(exception: ExceptionRecord) {
    Row(modifier = Modifier.padding(20.dp, 10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Center
        ) {
            ExpandableText(text = exception.stackTrace!!)
        }
    }
}

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 1,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier.clickable { expanded = !expanded }) {
        Text(
            text = cutText ?: text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState.value = it },
        )
        if (!expanded) {
            val density = LocalDensity.current
            Text(
                "... See more",

                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .padding(10.dp)
                    .then(
                        if (seeMoreOffset != null) Modifier.offset(
                            x = with(density) { seeMoreOffset.x.toDp() },
                            y = with(density) { seeMoreOffset.y.toDp() },
                        )
                        else Modifier
                    )
                    .clickable {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f)
            )
        }
    }
}
