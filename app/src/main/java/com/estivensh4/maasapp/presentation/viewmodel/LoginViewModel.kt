package com.estivensh4.maasapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estivensh4.maasapp.R
import com.estivensh4.maasapp.domain.model.Screen
import com.estivensh4.maasapp.domain.useCases.UseCases
import com.estivensh4.maasapp.util.Regex
import com.estivensh4.maasapp.util.UiEvent
import com.estivensh4.maasapp.util.containsRegex
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel(
    private val useCases: UseCases,
    context: Context
) : ViewModel() {

    private var _documentTypeList = MutableStateFlow<List<String>>(listOf())
    val documentTypeList = _documentTypeList.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var _documentType = MutableStateFlow("")
    val documentType = _documentType.asStateFlow()

    private var _documentNumber = MutableStateFlow("")
    val documentNumber = _documentNumber.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private var _enabled = MutableStateFlow(false)
    val enabled = _enabled.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _isErrorDocumentNumber = MutableStateFlow(false)
    val isErrorDocumentNumber = _isErrorDocumentNumber.asStateFlow()

    private var _isErrorPassword = MutableStateFlow(false)
    val isErrorPassword = _isErrorPassword.asStateFlow()

    init {
        _documentTypeList.value = context.resources.getStringArray(R.array.documents).toList()
    }

    fun onEvent(event: LoginEvents) {
        when (event) {
            LoginEvents.ValidateForm -> validateForm()
            LoginEvents.ValidateUser -> validateUser()
        }
    }

    private fun validateForm() {
        _enabled.value =
            _documentNumber.value.containsRegex(Regex.documentNumber) &&
                    _password.value.count() >= Regex.passwordDigits &&
                    _documentType.value.isNotEmpty()
    }

    fun setDocumentType(documentType: String) {
        _documentType.value = documentType
        validateForm()
    }

    fun setDocumentNumber(documentNumber: String) {
        _documentNumber.value = documentNumber
        validateForm()
        validateDocumentNumber()
    }

    fun setPassword(password: String) {
        _password.value = password
        validateForm()
        validatePassword()
    }

    private fun validateDocumentNumber() {
        _isErrorDocumentNumber.value = !_documentNumber.value.containsRegex(Regex.documentNumber)
    }

    private fun validatePassword() {
        _isErrorPassword.value = _password.value.length < 8
    }

    private fun validateUser() {
        _isLoading.value = true
        useCases.getUserUseCase(
            documentNumber = _documentNumber.value,
            documentType = _documentType.value,
            password = _password.value
        ).onEach {
            val route = if (it != null) Screen.DASHBOARD.name else "${Screen.FORM.name}?" +
                    "documentType=${_documentType.value}&" +
                    "documentNumber=${_documentNumber.value}&" +
                    "password=${_password.value}"
            delay(3000)
            _uiEvent.send(UiEvent.Navigate(route))
            _isLoading.value = false
        }.launchIn(viewModelScope)

    }

    sealed interface LoginEvents {
        data object ValidateForm : LoginEvents
        data object ValidateUser : LoginEvents
    }

}