package org.crys.gymrat.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.crys.gymrat.api.SimpleResponse
import org.crys.gymrat.register.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authResult = MutableStateFlow<SimpleResponse?>(null)
    val authResult = _authResult.asStateFlow()

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val response = authRepository.registerUser(email, password)
            _authResult.value = response
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = authRepository.loginUser(email, password)
            _authResult.value = response
        }
    }
}
