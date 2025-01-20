package org.crys.gymrat.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _gender = MutableStateFlow<String?>(null)
    val gender: StateFlow<String?> = _gender

    fun setName(newName: String) {
        _name.value = newName
    }

    fun setWeight(newWeight: String) {
        _weight.value = newWeight
    }

    fun setGender(newGender: String?) {
        _gender.value = newGender
    }

    fun saveOnboardingData(onComplete: () -> Unit) {
        viewModelScope.launch {
            // simulateSaveToDatabase()
            onComplete()
        }
    }
}