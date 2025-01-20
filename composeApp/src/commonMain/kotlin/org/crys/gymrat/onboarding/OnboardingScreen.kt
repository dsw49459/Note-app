package org.crys.gymrat.onboarding

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.crys.gymrat.navigation.Destinations
import org.crys.gymrat.utils.koinViewModel

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = koinViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val pageCount = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })

    OnboardingScreenContent(
        pagerState = pagerState,
        pageCount = pageCount,
        name = viewModel.name.collectAsState().value,
        weight = viewModel.weight.collectAsState().value,
        gender = viewModel.gender.collectAsState().value,
        onNameChange = viewModel::setName,
        onWeightChange = viewModel::setWeight,
        onGenderChange = viewModel::setGender,
        onClickNext = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        },
        onClickGetStarted = {
            viewModel.saveOnboardingData {
                navController.popBackStack()
                navController.navigate(Destinations.Home)
            }
        },
    )
}