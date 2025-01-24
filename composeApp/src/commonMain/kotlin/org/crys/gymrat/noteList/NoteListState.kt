package org.crys.gymrat.noteList

import org.crys.gymrat.noteList.model.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
