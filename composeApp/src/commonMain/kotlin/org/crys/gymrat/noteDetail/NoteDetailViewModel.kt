package org.crys.gymrat.noteDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.crys.gymrat.noteList.model.Note
import org.crys.gymrat.noteList.model.NoteDataRepository

class NoteDetailViewModel(
    private val noteDataRepository: NoteDataRepository
) : ViewModel() {

    private val _noteTitle = MutableStateFlow("")
    private val _isNoteTitleFocused = MutableStateFlow(false)
    private val _noteContent = MutableStateFlow("")
    private val _isNoteContentFocused = MutableStateFlow(false)
    private val _noteColor = MutableStateFlow(Note.generateRandomColor())

    private val _state = combine(
        _noteTitle,
        _isNoteTitleFocused,
        _noteContent,
        _isNoteContentFocused,
        _noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    val state = _state

    fun loadNoteFromRepository(noteId: String) {
        viewModelScope.launch {
            val note = noteDataRepository.getNoteById(noteId)
            note?.let {
                _noteTitle.value = it.title
                _noteContent.value = it.content
                _noteColor.value = note.color
            }

        }
    }

    fun onNoteTitleChanged(text: String) {
        _noteTitle.value = text
    }

    fun onNoteContentChanged(text: String) {
        _noteContent.value = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        _isNoteTitleFocused.value = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        _isNoteContentFocused.value = isFocused
    }

    fun saveNote(noteId: String) {
        viewModelScope.launch {
            noteDataRepository.insertNote(
                Note(
                    id = noteId,
                    title = _noteTitle.value,
                    content = _noteContent.value,
                    color = _noteColor.value,
                    owner = ""
                )
            )
        }
    }
}