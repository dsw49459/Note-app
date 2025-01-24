package org.crys.gymrat.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.crys.gymrat.noteList.model.Note
import org.crys.gymrat.noteList.model.NoteDataRepository
import org.crys.gymrat.noteList.model.SearchNotes

class NoteListViewModel(
    private val noteDataRepository: NoteDataRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _searchText = MutableStateFlow("")

    private val _isSearchActive = MutableStateFlow(false)

    val state: StateFlow<NoteListState> = combine(_notes, _searchText, _isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = SearchNotes().execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

    fun loadNotes() {
        viewModelScope.launch {
            val fetchedNotes = noteDataRepository.getAllNotes()
            _notes.value = fetchedNotes
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch() {
        _isSearchActive.value = !_isSearchActive.value
        if (!_isSearchActive.value) {
            _searchText.value = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataRepository.deleteNoteById(id)
            loadNotes()
        }
    }
}