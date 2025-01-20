package org.crys.gymrat.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreenContent(
    pageCount: Int,
    pagerState: PagerState,
    name: String,
    weight: String,
    gender: String?,
    onNameChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onGenderChange: (String?) -> Unit,
    onClickNext: () -> Unit,
    onClickGetStarted: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (pagerState.currentPage == pageCount - 1) {
                OnBoardingNavigationButton(
                    modifier = Modifier.padding(16.dp),
                    text = "Zacznij",
                    onClick = onClickGetStarted,
                )
            } else {
                OnBoardingNavigationButton(
                    modifier = Modifier.padding(16.dp),
                    text = "Dalej",
                    onClick = onClickNext,
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            HorizontalPager(
                modifier = Modifier
                    .weight(.9f)
                    .padding(16.dp),
                state = pagerState,
            ) { currentPage ->
                when (currentPage) {
                    0 -> NameInputPage(name, onNameChange)
                    1 -> WeightInputPage(weight, onWeightChange)
                    2 -> GenderSelectionPage(gender, onGenderChange)
                }
            }

            PageIndicators(
                pageCount = pageCount,
                currentPage = pagerState.currentPage,
            )
        }
    }
}

@Composable
private fun ColumnScope.PageIndicators(pageCount: Int, currentPage: Int) {
    Row(
        Modifier
            .weight(.1f)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (currentPage == iteration) {
                    Color.Gray
                } else {
                    Color.Gray.copy(
                        alpha = 0.2f,
                    )
                }
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .width(24.dp)
                    .height(8.dp),

                )
        }
    }
}

@Composable
fun OnBoardingNavigationButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun NameInputPage(name: String, onNameChange: (String) -> Unit) {
    PageContent(
        title = "Jak się nazywasz?",
        description = " ",
    ) {
        TextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = { Text("Podaj imię") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}


@Composable
fun WeightInputPage(weight: String, onWeightChange: (String) -> Unit) {
    PageContent(
        title = "Ile ważysz?",
        description = "Potrzebuje tej informacji, do monitorowania twojego progresu.",
    ) {
        TextField(
            value = weight,
            onValueChange = onWeightChange,
            placeholder = { Text("Podaj wagę w kg") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}


@Composable
fun GenderSelectionPage(gender: String?, onGenderChange: (String?) -> Unit) {
    PageContent(
        title = "Jakiej jesteś płci?",
        description = "Wybierz swoją płeć",
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderOption(
                label = "Mężczyzna",
                isSelected = gender == "Mężczyzna",
                onClick = { onGenderChange("Mężczyzna") },
            )
            GenderOption(
                label = "Kobieta",
                isSelected = gender == "Kobieta",
                onClick = { onGenderChange("Kobieta") },
            )
        }
    }
}

@Composable
fun GenderOption(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp)
    ) {
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
private fun PageContent(
    title: String,
    description: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = title,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = description,
        )
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}