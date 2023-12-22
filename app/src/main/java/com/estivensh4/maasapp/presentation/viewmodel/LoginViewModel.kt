package com.estivensh4.maasapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.estivensh4.maasapp.util.Regex
import com.estivensh4.maasapp.util.UiEvent
import com.estivensh4.maasapp.util.containsRegex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private var _documentTypeList = MutableStateFlow<List<String>>(listOf())
    val documentTypeList = _documentTypeList.asStateFlow()

    private var _uiEvent = MutableStateFlow<UiEvent>(UiEvent.Nothing)
    val uiEvent = _uiEvent.asStateFlow()

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

    init {
        _documentTypeList.value = listOf(
            "Cedula de ciudadania",
            "Cedula de extranjeria"
        )
    }

    fun onEvent(event: LoginEvents) {
        when (event) {
            LoginEvents.ValidateForm -> validateForm()
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
    }

    fun setPassword(password: String) {
        _password.value = password
        validateForm()
    }

    sealed interface LoginEvents {
        data object ValidateForm : LoginEvents
    }

}