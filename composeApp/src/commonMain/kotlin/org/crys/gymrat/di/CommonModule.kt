package org.crys.gymrat.di

import org.crys.gymrat.noteList.model.NoteDataRepository
import org.crys.gymrat.noteList.model.NoteDataRepositoryImpl
import org.koin.dsl.module

fun commonModule() = module {
    single<NoteDataRepository> { NoteDataRepositoryImpl() }
}
