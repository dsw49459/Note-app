package org.crys.gymrat.di

import io.ktor.client.HttpClient
import org.crys.gymrat.api.createHttpClient
import org.crys.gymrat.noteList.model.NoteDataRepository
import org.crys.gymrat.noteList.model.NoteDataRepositoryImpl
import org.koin.dsl.module

fun commonModule() = module {
    single<HttpClient> { createHttpClient() }
    single<NoteDataRepository> { NoteDataRepositoryImpl(get()) }
}
