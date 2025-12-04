package com.dhanush.repository

import com.dhanush.model.Note

interface NoteRepository {

    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: String)
    suspend fun getAllNotes(): List<Note>
    suspend fun getNoteById(id: String): Note?
}