package org.crys.gymrat.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.crys.gymrat.db.NoteDao
import org.crys.gymrat.db.NoteDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

private const val noteDatabaseFileName = "note_database.db"

actual val databaseModule: Module = module {
    factory { provideNoteDatabaseBuilder(context = get()) }
    single<NoteDatabase> { get<RoomDatabase.Builder<NoteDatabase>>().build() }
    factory<NoteDao> { get<NoteDatabase>().noteDao() }
}

private fun provideNoteDatabaseBuilder(context: Context): RoomDatabase.Builder<NoteDatabase> {
    val dbFile = context.getDatabasePath(noteDatabaseFileName)
    return Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        dbFile.absolutePath
    )
}