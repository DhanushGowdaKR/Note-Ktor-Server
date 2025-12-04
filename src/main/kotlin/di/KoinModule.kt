package com.dhanush.di

import com.dhanush.repository.NoteRepository
import com.dhanush.repository.NoteRepositoryImpl
import com.mongodb.client.MongoClients
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {
    single {
        val databaseName = "NoteDB"
        val userName = System.getenv("MONGODB_USERNAME")
        val userPassword = System.getenv("MONGODB_PASSWORD")
        val uri = "mongodb+srv://$userName:$userPassword@ktor.3umetgg.mongodb.net/?appName=ktor"
        val mongoClient = MongoClients.create(uri)
        val database = mongoClient.getDatabase(databaseName)
        database
    }
    singleOf(::NoteRepositoryImpl).bind<NoteRepository>()
}