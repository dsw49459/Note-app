package org.crys.gymrat.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.crys.gymrat.auth.AuthViewModel
import org.crys.gymrat.main.MainViewModel
import org.crys.gymrat.noteList.NoteListViewModel
import org.crys.gymrat.noteDetail.NoteDetailViewModel

actual val viewModelModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::NoteListViewModel)
    viewModelOf(::NoteDetailViewModel)
}
