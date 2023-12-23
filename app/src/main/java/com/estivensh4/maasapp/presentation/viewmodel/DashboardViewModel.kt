package com.estivensh4.maasapp.presentation.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estivensh4.maasapp.R
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.useCases.UseCases
import com.estivensh4.maasapp.util.UiEvent
import com.estivensh4.maasapp.util.getException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardViewModel(
    private val useCases: UseCases,
) : ViewModel(), KoinComponent {

    private val context: Context by inject()
    private var _cardNumber = MutableStateFlow("")
    val cardNumber = _cardNumber.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _enabled = MutableStateFlow(false)
    val enabled = _enabled.asStateFlow()

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var _cardList = MutableStateFlow<List<GetInformationOutput>>(listOf())
    val cardList = _cardList.asStateFlow()

    fun onEvent(event: DashboardEvents) {
        when (event) {
            DashboardEvents.ValidCard -> validateCard()
            is DashboardEvents.DeleteCard -> deleteCard(event.getInformationOutput)
        }
    }

    init {
        getAllCards()
    }

    private fun getAllCards() {
        useCases.getAllCardsUseCase()
            .onEach { _cardList.value = it }
            .launchIn(viewModelScope)
    }

    private fun deleteCard(getInformationOutput: GetInformationOutput) {
        viewModelScope.launch {
            removeCardFromList(getInformationOutput)
            useCases.deleteCardUseCase(getInformationOutput.cardNumber)
        }
    }

    private fun validateCard() {
        viewModelScope.launch {
            _isLoading.value = true
            useCases.validCardUseCase(_cardNumber.value)
                .onSuccess { validCardOutput ->
                    if (validCardOutput.isValid) {
                        useCases.getInformationCardUseCase(_cardNumber.value)
                            .onSuccess { getInformationOutput ->
                                useCases.getCardUseCase(getInformationOutput.cardNumber)
                                    .onEach { informationOutput ->
                                        if (informationOutput == null) {
                                            useCases.insertCardUseCase(getInformationOutput)
                                            _cardList.value =
                                                _cardList.value.plus(getInformationOutput)
                                            _cardNumber.value = ""
                                            _isLoading.value = false
                                            _uiEvent.send(UiEvent.CloseModal)
                                        } else {
                                            _isLoading.value = false
                                            _uiEvent.send(
                                                UiEvent.ShowMessage(
                                                    ContextCompat.getString(
                                                        context,
                                                        R.string.text_already_card
                                                    )
                                                )
                                            )
                                        }
                                    }.launchIn(this)
                            }.onFailure {
                                val exception = it.getException()
                                _isLoading.value = false
                                _uiEvent.send(UiEvent.ShowMessage(exception.errorMessage))
                            }
                    } else {
                        _uiEvent.send(
                            UiEvent.ShowMessage(
                                ContextCompat.getString(
                                    context,
                                    R.string.text_card_invalid
                                )
                            )
                        )
                    }
                }.onFailure {
                    val exception = it.getException()
                    _isLoading.value = false
                    _uiEvent.send(UiEvent.ShowMessage(exception.errorMessage))
                }
        }
    }

    private fun removeCardFromList(card: GetInformationOutput) {
        _cardList.value = _cardList.value.minus(card)
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
        data class DeleteCard(val getInformationOutput: GetInformationOutput) : DashboardEvents
    }
}