@file:OptIn(ExperimentalMaterial3Api::class)

package com.cb.cbcommon.presentation.page

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cb.cbcommon.BaseApplication
import com.cb.cbcommon.R
import com.cb.cbcommon.data.dto.Person
import com.cb.cbtools.presentation.common.CbAppBar
import com.cb.cbtools.presentation.common.CbListItem
import com.cb.cbtools.presentation.common.CbListItemActionCustom
import com.cb.cbtools.presentation.common.CbListItemIconDrawablePrimary
import com.cb.cbtools.presentation.common.CbListItemIconImageVectorSecondary
import com.cb.cbtools.presentation.common.CbListItemSummary
import com.cb.cbtools.presentation.common.CbListItemTitle
import com.cb.cbtools.util.IconUtil
import java.util.Locale

val names = listOf(
    "Person 1",
    "Person 2",
    "Person 3",
    "Person 4",
    "Person 5",
    "Person 6",
    "Person 7",
    "Person 8",
    "Person 9",
    "Person 10"
)

val dp = listOf(
    R.drawable.pic1,
    R.drawable.pic2,
    R.drawable.pic3,
    R.drawable.pic4,
    R.drawable.pic5,
    R.drawable.pic6,
    R.drawable.pic7,
    R.drawable.pic8,
    R.drawable.pic9,
    R.drawable.pic10,
)
val firstNames = listOf(
    "Akshay",
    "Pankaj",
    "John",
    "Jane",
    "Alice",
    "Bob",
    "Charlie",
    "David",
    "Emily",
    "Frank"
)
val lastNames = listOf(
    "Smith",
    "Johnson",
    "Williams",
    "Jones",
    "Brown",
    "Davis",
    "Miller",
    "Wilson",
    "Moore",
    "Taylor"
)

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
    val person = remember {
        mutableStateOf(generateRandomPerson())
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CbListItem(
            iconUnit = {
                CbListItemIconDrawablePrimary(
                    drawable = BaseApplication.getInstance().getDrawable(person.value.dpDrawable)
                ) {

                }
            },
            actionUnit = {
                CbListItemActionCustom {
                    CbListItemIconImageVectorSecondary(
                        imageVector = Icons.Default.Refresh,
                    ) {
                        person.value = generateRandomPerson()
                    }
                }
            },
            titleUnit = { CbListItemTitle(text = person.value.name) },
            summaryUnit = { CbListItemSummary(text = person.value.message) },

            )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledButton("DP") {
                person.value = Person(person.value.name, dp.random(), person.value.message)

            }
            Spacer(modifier = Modifier.width(5.dp))
            StyledButton("Name") {
                person.value =
                    Person(generateRandomName(), person.value.dpDrawable, person.value.message)
            }
            Spacer(modifier = Modifier.width(5.dp))
            StyledButton("Message") {
                person.value =
                    Person(person.value.name, person.value.dpDrawable, generateRandomSentence())

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            StyledButton(
                "Send"
            ) {
                BaseApplication.getInstance().notificationWriteHelper.showNotification(
                    title = person.value.name,
                    text = person.value.message,
                    largeIcon = IconUtil.drawableToBitmap(
                        BaseApplication.getInstance().getDrawable(person.value.dpDrawable)
                    )
                )
            }
        }
    }
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
    return Person(generateRandomName(), dp.random(), generateRandomSentence())
}

fun generateRandomName(): String {
    val firstName = firstNames.random()
    val lastName = lastNames.random()
    return "$firstName $lastName"
}

fun generateRandomSentence(): String {
    val subjects = listOf("The cat", "A dog", "My friend", "A bird", "The sun")
    val verbs = listOf("jumps", "runs", "flies", "sings", "sleeps")
    val objects = listOf(
        "over the moon",
        "through the forest",
        "with joy",
        "under the table",
        "around the city"
    )

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