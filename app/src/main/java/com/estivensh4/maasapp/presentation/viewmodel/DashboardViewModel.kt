package com.estivensh4.maasapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estivensh4.maasapp.domain.useCases.UseCases
import com.estivensh4.maasapp.util.UiEvent
import com.estivensh4.maasapp.util.getException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val useCases: UseCases
) : ViewModel() {

    private var _cardNumber = MutableStateFlow("")
    val cardNumber = _cardNumber.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _enabled = MutableStateFlow(false)
    val enabled = _enabled.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: DashboardEvents) {
        when (event) {
            DashboardEvents.ValidCard -> validateCard()
        }
    }

    private fun validateCard() {
        viewModelScope.launch {
            _isLoading.value = true
            useCases.validCardUseCase(_cardNumber.value)
                .onSuccess { validCardOutput ->
                    _isLoading.value = false
                    if (validCardOutput.isValid) {
                        // registramos tarjeta
                    } else {
                        _uiEvent.send(UiEvent.ShowMessage("La tarjeta no es valida"))
                    }
                }.onFailure {
                    val exception = it.getException()
                    _isLoading.value = false
                    _uiEvent.send(UiEvent.ShowMessage(exception.errorMessage))
                }
        }
    }

    fun setCardNumber(cardNumber: String) {
        _cardNumber.value = cardNumber
        validateButton()
    }

    private fun validateButton() {
        _enabled.value = _cardNumber.value.isNotEmpty()
    }

    sealed interface DashboardEvents {
        data object ValidCard : DashboardEvents
    }
}