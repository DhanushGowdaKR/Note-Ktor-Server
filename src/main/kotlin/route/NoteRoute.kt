package com.dhanush.route

import com.dhanush.model.Note
import com.dhanush.repository.NoteRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.sse
import io.ktor.sse.ServerSentEvent
import kotlinx.coroutines.delay

fun Application.noteRoute(
    noteRepository: NoteRepository
) {
    routing {
        route("note") {
            post("/add") {
                val note = call.receive<Note>()
                noteRepository.addNote(note)
                call.respond("note added")
            }
            put("/update") {
                val note = call.receive<Note>()
                noteRepository.updateNote(note)
                call.respond("note updated")
            }
            delete("/delete") {
                val id = call.queryParameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                noteRepository.deleteNote(id)
                call.respond("note deleted")
            }
            get("/get-by-id") {
                val id = call.queryParameters["id"] ?: return@get call.respond("id not found")
                val note = noteRepository.getNoteById(id) ?: return@get call.respond("note not found")
                call.respond(note)
            }
            sse("/get-stream") {
                repeat(10) {
                    send(data = "get-stream $it")
                    delay(1000)
                }
            }
            sse("/get-all") {
                noteRepository.getAllNotes().collect {
                    send(it.toString())
                }
            }
        }
    }
}