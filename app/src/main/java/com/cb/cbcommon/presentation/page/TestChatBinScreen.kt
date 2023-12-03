@file:OptIn(ExperimentalMaterial3Api::class)

package com.cb.cbcommon.presentation.page

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.data.constant.TestConstants.dpDrawables
import com.cb.cbcommon.data.constant.TestConstants.firstNames
import com.cb.cbcommon.data.constant.TestConstants.lastNames
import com.cb.cbcommon.data.constant.TestConstants.objects
import com.cb.cbcommon.data.constant.TestConstants.subjects
import com.cb.cbcommon.data.constant.TestConstants.verbs
import com.cb.cbcommon.data.dto.Person
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemActionCustom
import com.cb.cbtools.presentation.common.CbListItemIconDrawablePrimary
import com.cb.cbtools.presentation.common.CbListItemIconImageVectorSecondary
import com.cb.cbtools.presentation.common.CbListItemSummary
import com.cb.cbtools.presentation.common.CbListItemTitle
import com.cb.cbtools.presentation.common.NumberSlider
import com.cb.cbtools.util.IconUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale



@ExperimentalAnimationApi
@Composable
fun TestChatBinScreen(
    navController: NavController,
) {


    Scaffold(
        topBar = {
            CbAppBar(
                title = "Test chat bin",
                backAction = { navController.navigateUp() },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
            )
            {
                Spacer(modifier = Modifier.height(10.dp))
                TestChatBin()
            }
        }
    }
}

@Composable
fun TestChatBin() {
    var person by remember {
        mutableStateOf(generateRandomPerson())
    }

    var stop by remember {
        mutableStateOf(false)
    }
    var personCount by remember {
        mutableStateOf(1)
    }

    var messageCount by remember {
        mutableStateOf(1)
    }

    var currentPerson by remember {
        mutableStateOf(1)
    }

    var currentMessage by remember {
        mutableStateOf(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {

        CbListItem(
            iconUnit = {
                CbListItemIconDrawablePrimary(
                    drawable = BaseApplication.getInstance().getDrawable(person.dpDrawable)
                ) {

                }
            },
            actionUnit = {
                CbListItemActionCustom {
                    CbListItemIconImageVectorSecondary(
                        imageVector = Icons.Default.Refresh,
                    ) {
                        person = generateRandomPerson()
                    }
                }
            },
            titleUnit = { CbListItemTitle(text = person.name) },
            summaryUnit = { CbListItemSummary(text = person.message) },

            )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledButton("DP") {
                person = Person(person.name, dpDrawables.random(), person.message)

            }
            Spacer(modifier = Modifier.width(5.dp))
            StyledButton("Name") {
                person =
                    Person(generateRandomName(), person.dpDrawable, person.message)
            }
            Spacer(modifier = Modifier.width(5.dp))
            StyledButton("Message") {
                person =
                    Person(person.name, person.dpDrawable, generateRandomSentence())

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            StyledButton(
                "Send"
            ) {
                sendNotification(person)
            }
        }

        NumberSlider(
            title = "Update sender count",
            range = 0f..100f,
            value = personCount
        ) {
            personCount = it
        }
        NumberSlider(
            title = "Update message count",
            range = 0f..1000f,
            value = messageCount
        ) {
            messageCount = it
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledButton(
                "Send"
            ) {
                Handler(Looper.getMainLooper()).postDelayed({
                    CoroutineScope(Dispatchers.IO).launch {
                        for (i in 0..personCount) {
                            currentPerson = i
                            for (j in 0..messageCount) {
                                if (stop) break;
                                currentMessage = i
                                sendNotification(generateRandomPerson())
                            }
                        }
                    }
                }, 100)


            }

            Spacer(modifier = Modifier.width(5.dp))
            StyledButton("Stop") {
                stop = true
            }

        }
        CbListItem(
            titleUnit = {
                CbListItemTitle(
                    text =
                    "Person : $currentPerson, message : $currentMessage"
                )
            },
        )

    }
}

fun sendNotification(person: Person) {
    BaseApplication.getInstance().notificationWriteHelper.showNotification(
        title = person.name,
        text = person.message,
        largeIcon = IconUtil.drawableToBitmap(
            BaseApplication.getInstance().getDrawable(person.dpDrawable)
        )
    )
}


@Composable
fun StyledButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(120.dp)
            .height(50.dp)
            .clip(CircleShape)
            .padding(2.dp)
    ) {
        Text(text = text)
    }
}

fun generateRandomPerson(): Person {
    return Person(generateRandomName(), dpDrawables.random(), generateRandomSentence())
}

fun generateRandomName(): String {
    val firstName = firstNames.random()
    val lastName = lastNames.random()
    return "$firstName $lastName"
}

fun generateRandomSentence(): String {


    val randomSentence = buildString {
        append(subjects.random())
        append(" ")
        append(verbs.random())
        append(" ")
        append(objects.random())
        append(".")
    }

    return randomSentence.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}