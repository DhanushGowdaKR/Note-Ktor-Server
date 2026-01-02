package com.dhanush.repository

import com.dhanush.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: String)
    suspend fun getNoteById(id: String): Note?

        fun getAllNotes(): Flow<List<Note>>
//    suspend fun getAllNotes(): List<Note>
}