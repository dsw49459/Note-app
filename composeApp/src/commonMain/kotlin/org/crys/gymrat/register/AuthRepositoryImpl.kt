package org.crys.gymrat.register

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.crys.gymrat.api.AccountRequest
import org.crys.gymrat.api.SimpleResponse

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {

    private val baseUrl = "http://10.0.2.2:8001"
    private var accountRequest: AccountRequest? = null

    override suspend fun registerUser(email: String, password: String): SimpleResponse {
        val response: HttpResponse = httpClient.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(AccountRequest(email, password))
        }
        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            SimpleResponse(false, "Registration failed with status: ${response.status.value}")
        }
    }

    override suspend fun loginUser(email: String, password: String): SimpleResponse {
        val response: HttpResponse = httpClient.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(AccountRequest(email, password))
        }
        return if (response.status == HttpStatusCode.OK) {
            accountRequest = AccountRequest(email, password)
            response.body()
        } else {
            SimpleResponse(false, "Login failed with status: ${response.status.value}")
        }
    }

    override fun getAccountRequest(): AccountRequest? {
        return accountRequest
    }
}
