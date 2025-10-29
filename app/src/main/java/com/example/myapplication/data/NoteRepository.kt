package com.example.myapplication.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteRepository {
    private val _notes = MutableStateFlow(emptyList<Note>())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(note: Note) {
        _notes.update { currentNotes -> currentNotes + note }
    }

    fun getNoteById(id: Long): Note? {
        return _notes.value.find { it.id == id }
    }

    fun updateNote(note: Note) {
        _notes.update { currentNotes ->
            currentNotes.map {
                if (it.id == note.id) note else it
            }
        }
    }

    fun deleteNote(note: Note) {
        _notes.update { currentNotes -> currentNotes - note }
    }

    companion object {
        val sampleData = listOf(
            Note(title = "First Note", content = "This is the content of the first note."),
            Note(title = "Second Note", content = "This is the content of the second note."),
            Note(title = "Third Note", content = "This is the content of the third note.")
        )
    }

    init {
        _notes.value = sampleData
    }
}
