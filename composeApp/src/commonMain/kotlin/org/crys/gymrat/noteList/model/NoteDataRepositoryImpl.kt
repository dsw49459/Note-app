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
import org.crys.gymrat.db.NoteDao
import org.crys.gymrat.register.AuthRepository

class NoteDataRepositoryImpl(
    private val httpClient: HttpClient,
    private val authRepository: AuthRepository,
    private val noteDao: NoteDao
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
        try {
            val response: HttpResponse = httpClient.post("$baseUrl/addNote") {
                contentType(ContentType.Application.Json)
                setBody(updatedNote)
                addAuthorizationHeader()
            }
            if (response.status == HttpStatusCode.OK) {
                // sukces – zapisujemy lokalnie jako zsynchronizowaną
                noteDao.insert(updatedNote.copy(isSynchronized = true).toEntity())
            } else {
                // błąd z backendu – zapisujemy lokalnie jako niezsynchronizowaną
                noteDao.insert(updatedNote.copy(isSynchronized = false).toEntity())
            }
        } catch (e: Exception) {
            // brak internetu, timeout, etc.
            noteDao.insert(updatedNote.copy(isSynchronized = false).toEntity())
        }
    }


    override suspend fun getNoteById(id: String): Note? {
        return noteDao.getById(id)?.toNote(authRepository.getAccountRequest()!!.email)
    }


    override suspend fun getAllNotes(): List<Note> {
        val response: HttpResponse = httpClient.get("$baseUrl/getNotes") {
            header("Accept", "application/json")
            addAuthorizationHeader()
        }
        return response.body()
    }

    override suspend fun deleteNoteById(id: String) {
        try {
            val response: HttpResponse = httpClient.post("$baseUrl/deleteNote") {
                contentType(ContentType.Application.Json)
                setBody(DeleteNoteRequest(id))
                addAuthorizationHeader()
            }
            if (response.status != HttpStatusCode.OK) {
                throw Exception("Backend error: ${response.status.description}")
            }
        } catch (e: Exception) {
            // remove exception
        } finally {
            noteDao.deleteById(id)
        }
    }


    override suspend fun syncNotesWithBackend() {
        val unsyncedNotes = noteDao.getAll().filter { !it.isSynchronized }
        for (noteEntity in unsyncedNotes) {
            val note = noteEntity.toNote(authRepository.getAccountRequest()!!.email)
            try {
                val response = httpClient.post("$baseUrl/addNote") {
                    contentType(ContentType.Application.Json)
                    setBody(note.copy(owner = authRepository.getAccountRequest()!!.email))
                    addAuthorizationHeader()
                }
                if (response.status == HttpStatusCode.OK) {
                    noteDao.insert(note.copy(isSynchronized = true).toEntity())
                }
            } catch (_: Exception) {
                // brak internetu, ignorujemy
            }
        }
    }
}