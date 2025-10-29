package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Note
import com.example.myapplication.data.NoteRepository
import kotlinx.coroutines.flow.StateFlow

class NotesViewModel(private val repository: NoteRepository = NoteRepository()) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.notes

    fun addNote(note: Note) {
        repository.addNote(note)
    }

    fun getNoteById(id: Long): Note? {
        return repository.getNoteById(id)
    }

    fun updateNote(note: Note) {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }
}
