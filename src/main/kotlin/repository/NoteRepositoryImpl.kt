package com.dhanush.repository

import com.dhanush.model.Note
import com.dhanush.model.toDocument
import com.dhanush.model.toNote
import com.dhanush.utils.MongoConfig
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import kotlin.collections.map

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

    override fun getAllNotes(): Flow<List<Note>> = callbackFlow {
        trySend(notesCollection.find().map { it.toNote() }.toList())
        val changeStream = notesCollection.watch()
        val job = launch {
            changeStream.asFlow().collect { _ ->
                val notes = notesCollection.find().map { it.toNote() }.toList()
                trySend(notes)
            }
        }
        awaitClose { job.cancel() }
    }

//    override suspend fun getAllNotes(): List<Note> {
//        return notesCollection.find().map { it.toNote() } .toList()
//    }

    override suspend fun getNoteById(id: String): Note? {
        return notesCollection.find(Filters.eq("id", id)).firstOrNull()?.toNote()
    }
}