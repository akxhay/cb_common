package com.cb.cbtools.presentation.composable.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cb.cbtools.composables.CbAppBar
import com.cb.cbtools.composables.CbDecisionDialog
import com.cb.cbtools.composables.CbNoResult
import com.cb.cbtools.data.model.ExceptionRecord
import com.cb.cbtools.dynamic.DynamicConfig
import com.cb.cbtools.presentation.viewModel.ExceptionViewModel
import com.cb.cbtools.util.DateUtil
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun ExceptionScreen(
    navController: NavController,
    dynamicConfig: DynamicConfig,
    viewModel: ExceptionViewModel = hiltViewModel()
) {
    val exceptions by viewModel.exceptions.observeAsState(initial = emptyList())
    val showAlert = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            CbAppBar(
                title = "Exceptions",
                backAction = { navController.navigateUp() },
                actions = {
                    IconButton(onClick = {
                        showAlert.value = true
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            "Delete",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                dynamicConfig = dynamicConfig
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (exceptions.isNotEmpty()) {
                ExceptionLoaded(exceptions)
            } else {
                CbNoResult(
                    dynamicConfig = dynamicConfig,
                    text = "No exceptions"
                )
            }
        }
        if (showAlert.value) {
            DeleteExceptionAlertDialog(
                LocalContext.current,
                viewModel,
                showAlert,
                navController,
                dynamicConfig
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
    dynamicConfig: DynamicConfig,

    ) {
    CbDecisionDialog(
        showAlert = showAlert,
        onConfirmClick = {
            Toast.makeText(context, "Exceptions are being deleted", Toast.LENGTH_SHORT)
                .show()
            viewModel.deleteRecord()
            showAlert.value = false
            navController.navigateUp()
        },
        title = "Please confirm",
        text = "Delete all exception?",
        dynamicConfig = dynamicConfig
    )

}


@Composable
fun ExceptionLoaded(
    exceptions: List<ExceptionRecord>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surface
            )
    ) {
        items(exceptions) {
            ExceptionItem(
                exception = it
            )
        }
    }

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
    )
    {
        Column {
            Row(modifier = Modifier.padding(20.dp, 10.dp)) {
                Box(
                    contentAlignment = Center
                ) {

                    Text(
                        text = exception.className!! + " : " + exception.lineNumber + " : " + DateUtil.convertLogDate(
                            exception.time
                        ),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
            if (exception.exceptionDetails != null) {

                Row(modifier = Modifier.padding(20.dp, 10.dp)) {

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Center
                    ) {

                        Text(
                            text = exception.exceptionDetails!!,
                            style = TextStyle(
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            overflow = TextOverflow.Ellipsis,
                        )

                    }
                }
            }
            if (exception.stackTrace != null) {
                Row(modifier = Modifier.padding(20.dp, 10.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentAlignment = Center
                    ) {
                        ExpandableText(
                            text = exception.stackTrace!!,
                            minimizedMaxLines = 5
                        )
                    }
                }
            }
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
                        if (seeMoreOffset != null)
                            Modifier.offset(
                                x = with(density) { seeMoreOffset.x.toDp() },
                                y = with(density) { seeMoreOffset.y.toDp() },
                            )
                        else
                            Modifier
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