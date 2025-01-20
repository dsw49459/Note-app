package org.crys.gymrat.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.crys.gymrat.navigation.AppNavHost

@Composable
fun MainScreen(
    navController: NavHostController,
    onBoardingCompleted: Boolean,
) {
    AppNavHost(
        completedOnboarding = onBoardingCompleted,
        navController = navController
    )
}