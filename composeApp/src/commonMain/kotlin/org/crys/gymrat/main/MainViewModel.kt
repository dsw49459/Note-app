package org.crys.gymrat.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.crys.gymrat.noteList.model.NoteDataRepository

class MainViewModel(
    noteDataRepository: NoteDataRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            noteDataRepository.syncNotesWithBackend()
        }
    }
}