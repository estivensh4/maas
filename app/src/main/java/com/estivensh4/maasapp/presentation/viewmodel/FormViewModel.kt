package com.estivensh4.maasapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estivensh4.maasapp.domain.model.Screen
import com.estivensh4.maasapp.domain.model.User
import com.estivensh4.maasapp.domain.useCases.UseCases
import com.estivensh4.maasapp.util.Regex
import com.estivensh4.maasapp.util.UiEvent
import com.estivensh4.maasapp.util.containsRegex
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormViewModel(
    private val useCases: UseCases
) : ViewModel() {

    private var _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private var _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _enabled = MutableStateFlow(false)
    val enabled = _enabled.asStateFlow()

    private var _uiEvent = MutableStateFlow<UiEvent>(UiEvent.Nothing)
    val uiEvent = _uiEvent.asStateFlow()

    private var _isErrorFullName = MutableStateFlow(false)
    val isErrorFullName = _isErrorFullName.asStateFlow()

    private var _isErrorAddress = MutableStateFlow(false)
    val isErrorAddress = _isErrorAddress.asStateFlow()

    private var _isErrorEmail = MutableStateFlow(false)
    val isErrorEmail = _isErrorEmail.asStateFlow()

    fun onEvent(event: FormEvents) {
        when (event) {
            is FormEvents.RegisterUser -> registerUser(
                documentType = event.documentType,
                documentNumber = event.documentNumber,
                password = event.password
            )
        }
    }

    fun setFullName(fullName: String) {
        _fullName.value = fullName
        validateButton()
        validateFullName()
    }

    fun setAddress(address: String) {
        _address.value = address
        validateButton()
        validateAddress()
    }

    fun setEmail(email: String) {
        _email.value = email
        validateButton()
        validateEmail()
    }

    private fun validateFullName() {
        _isErrorFullName.value = !_fullName.value.containsRegex(Regex.fullName)
    }

    private fun validateAddress() {
        _isErrorAddress.value = !_address.value.containsRegex(Regex.address)
    }

    private fun validateEmail() {
        _isErrorEmail.value = !_email.value.containsRegex(Regex.email)
    }

    private fun validateButton() {
        _enabled.value = _fullName.value.containsRegex(Regex.fullName) &&
                _address.value.containsRegex(Regex.address) &&
                _email.value.containsRegex(Regex.email)
    }

    private fun registerUser(
        documentType: String,
        documentNumber: String,
        password: String
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            useCases.insertUserUseCase(
                User(
                    documentType = documentType,
                    documentNumber = documentNumber,
                    password = password,
                    email = _email.value,
                    fullName = _fullName.value,
                    address = _address.value
                )
            )
            delay(2000)
            _isLoading.value = false
            _uiEvent.value = UiEvent.Navigate(Screen.DASHBOARD.name)
        }
    }


    sealed interface FormEvents {
        data class RegisterUser(
            val documentType: String,
            val documentNumber: String,
            val password: String
        ) : FormEvents
    }
}