package org.crys.gymrat.di

import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import org.crys.gymrat.onboarding.OnboardingViewModel
import org.crys.gymrat.main.MainViewModel
import org.crys.gymrat.noteList.NoteListViewModel
import org.crys.gymrat.noteDetail.NoteDetailViewModel

actual val viewModelModule = module {
    singleOf(::OnboardingViewModel)
    singleOf(::MainViewModel)
    singleOf(::NoteListViewModel)
    singleOf(::NoteListViewModel)
    singleOf(::NoteDetailViewModel)
}
