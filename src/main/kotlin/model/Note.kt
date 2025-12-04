package com.dhanush.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Note(
    @BsonId val id: String = ObjectId().toHexString(),
    val title: String,
    val description: String
)

fun Note.toDocument(): Document {
    return Document.parse(Json.encodeToString(this))
}

fun Document.toNote(): Note {
    val json = Json {
        ignoreUnknownKeys = true
    }
    return json.decodeFromString(this.toJson())
}