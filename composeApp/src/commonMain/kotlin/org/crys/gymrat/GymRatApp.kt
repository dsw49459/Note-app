package org.crys.gymrat

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.crys.gymrat.main.MainScreen
import org.crys.gymrat.main.MainViewModel
import org.crys.gymrat.utils.koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun GymRatApp(
    mainViewModel: MainViewModel = koinViewModel()
) {
    KoinContext {

        val navController = rememberNavController()

        MainScreen(
            navController = navController
        )
    }
}