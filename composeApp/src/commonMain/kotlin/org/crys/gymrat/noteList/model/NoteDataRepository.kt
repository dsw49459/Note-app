package org.crys.gymrat.noteList.model

interface NoteDataRepository {
    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id: String): Note?
    suspend fun getAllNotes(): List<Note>
    suspend fun deleteNoteById(id: String)
}