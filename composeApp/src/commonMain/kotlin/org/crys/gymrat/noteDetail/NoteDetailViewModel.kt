package org.crys.gymrat.noteDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.crys.gymrat.noteList.model.Note
import org.crys.gymrat.noteList.model.NoteDataRepository

class NoteDetailViewModel(
    private val noteDataRepository: NoteDataRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused", false)
    private val noteColor = savedStateHandle.getStateFlow(
        "noteColor",
        Note.generateRandomColor()
    )

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if(existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataRepository.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle["noteContent"] = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteContentFocused"] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataRepository.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}