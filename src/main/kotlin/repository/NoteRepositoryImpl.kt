package com.dhanush.repository

import com.dhanush.model.Note
import com.dhanush.model.toDocument
import com.dhanush.model.toNote
import com.dhanush.utils.MongoConfig
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.types.ObjectId

class NoteRepositoryImpl(
    private val database: MongoDatabase
) : NoteRepository {
    private val notesCollection = database.getCollection(MongoConfig.NOTES_COLLECTION)

    override suspend fun addNote(note: Note) {
        notesCollection.insertOne(note.toDocument())
    }

    override suspend fun updateNote(note: Note) {
        notesCollection.findOneAndReplace(Filters.eq("id", note.id), note.toDocument())
    }

    override suspend fun deleteNote(id: String) {
        notesCollection.findOneAndDelete(Filters.eq("id", id))
    }

    override suspend fun getAllNotes(): List<Note> {
        return notesCollection.find().map { it.toNote() }.toList()
    }

    override suspend fun getNoteById(id: String): Note? {
        return notesCollection.find(Filters.eq("id", id)).firstOrNull()?.toNote()
    }
}