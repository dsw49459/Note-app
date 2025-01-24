package org.crys.gymrat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.crys.gymrat.noteList.NoteListScreen
import org.crys.gymrat.onboarding.OnboardingScreen

@Composable
fun AppNavHost(
    completedOnboarding: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (completedOnboarding) Destinations.Home else Destinations.Onboarding
    ) {
        composable<Destinations.Onboarding> {
            NoteListScreen(
                navController = navController
            )
        }
    }
}