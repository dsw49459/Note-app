package org.crys.gymrat.noteList.model

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.util.encodeBase64
import io.ktor.utils.io.core.toByteArray
import org.crys.gymrat.api.DeleteNoteRequest
import org.crys.gymrat.register.AuthRepository

class NoteDataRepositoryImpl(
    private val httpClient: HttpClient,
    private val authRepository: AuthRepository
) : NoteDataRepository {

    private val baseUrl = "http://10.0.2.2:8001"

    private fun getAuthorizationHeader(): String? {
        val accountRequest = authRepository.getAccountRequest()
        return accountRequest?.let {
            val credentials = "${it.email}:${it.password}"
            val base64Credentials = credentials.toByteArray().encodeBase64()
            "Basic $base64Credentials"
        }
    }

    private fun HttpRequestBuilder.addAuthorizationHeader() {
        getAuthorizationHeader()?.let { header("Authorization", it) }
    }

    override suspend fun insertNote(note: Note) {
        val updatedNote = note.copy(owner = authRepository.getAccountRequest()!!.email)
        val response: HttpResponse = httpClient.post("$baseUrl/addNote") {
            contentType(ContentType.Application.Json)
            setBody(updatedNote)
            addAuthorizationHeader()
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Failed to insert note: ${response.status.description}")
        }
    }

    override suspend fun getNoteById(id: String): Note? {
        val response: HttpResponse = httpClient.get("$baseUrl/getNoteById") {
            parameter("id", id)
            addAuthorizationHeader()
        }
        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun getAllNotes(): List<Note> {
        val response: HttpResponse = httpClient.get("$baseUrl/getNotes") {
            header("Accept", "application/json")
            addAuthorizationHeader()
        }
        return response.body()
    }

    override suspend fun deleteNoteById(id: String) {
        val response: HttpResponse = httpClient.post("$baseUrl/deleteNote") {
            contentType(ContentType.Application.Json)
            setBody(DeleteNoteRequest(id))
            addAuthorizationHeader()
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Failed to delete note: ${response.status.description}")
        }
    }
}