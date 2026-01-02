package com.dhanush

import com.dhanush.di.configureKoin
import com.dhanush.repository.NoteRepository
import com.dhanush.route.noteRoute
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.sse.SSE
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            },
            contentType = ContentType.Application.Json
        )
    }
    install(SSE)
    val repository by inject<NoteRepository>()
    noteRoute(repository)

}
