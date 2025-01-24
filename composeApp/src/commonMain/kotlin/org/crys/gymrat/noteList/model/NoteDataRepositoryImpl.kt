package org.crys.gymrat.noteList.model

class NoteDataRepositoryImpl : NoteDataRepository {

    private val notes = mutableListOf<Note>()

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun getNoteById(id: Long): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun getAllNotes(): List<Note> {
        return notes
    }

    override suspend fun deleteNoteById(id: Long) {
        notes.remove(notes.find { it.id == id })
    }
}