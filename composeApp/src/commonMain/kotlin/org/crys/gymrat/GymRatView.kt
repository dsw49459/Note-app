package org.crys.gymrat

import androidx.compose.runtime.*
import org.crys.gymrat.onboarding.OnboardingScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun GymRatView() {
    KoinContext {
        OnboardingScreen()
    }
}