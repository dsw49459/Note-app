package org.crys.gymrat.register

import org.crys.gymrat.api.SimpleResponse

interface AuthRepository {
    suspend fun registerUser(email: String, password: String): SimpleResponse
    suspend fun loginUser(email: String, password: String): SimpleResponse
}