package org.crys.gymrat.noteList.model

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class NoteDataRepositoryImpl(
    private val httpClient: HttpClient
) : NoteDataRepository {

    private val baseUrl = "http://10.0.2.2:8001"

    override suspend fun insertNote(note: Note) {
        val response: HttpResponse = httpClient.post("$baseUrl/addNote") {
            contentType(ContentType.Application.Json)
            setBody(note)
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Failed to insert note: ${response.status.description}")
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        val response: HttpResponse = httpClient.get("$baseUrl/getNoteById") {
            parameter("id", id)
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else if (response.status == HttpStatusCode.NotFound) {
            return null // Jeśli notatka nie istnieje
        } else {
            throw Exception("Failed to fetch note by ID: ${response.status.description}")
        }
    }

    override suspend fun getAllNotes(): List<Note> {
        return httpClient.get("$baseUrl/notes").body()
    }

    override suspend fun deleteNoteById(id: Long) {
        val response: HttpResponse = httpClient.post("$baseUrl/deleteNote") {
            contentType(ContentType.Application.Json)
            //setBody(DeleteNoteRequest(id))
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Failed to delete note: ${response.status.description}")
        }
    }
}