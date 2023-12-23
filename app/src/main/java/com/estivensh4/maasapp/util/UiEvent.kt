package com.estivensh4.maasapp.util

sealed interface UiEvent {
    data class ShowMessage(val message: String) : UiEvent
    data object CloseModal : UiEvent
    data class Navigate(val screen: String) : UiEvent
}