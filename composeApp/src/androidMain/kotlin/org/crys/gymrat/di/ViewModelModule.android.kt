package org.crys.gymrat.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.crys.gymrat.onboarding.OnboardingViewModel
import org.crys.gymrat.main.MainViewModel
import org.crys.gymrat.noteList.NoteListViewModel

actual val viewModelModule = module {
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::NoteListViewModel)
}
