package com.estivensh4.maasapp.util

sealed interface UiEvent {
    data class ShowMessage(val message: String): UiEvent
    data class Navigate(val screen: String): UiEvent
    data object Nothing: UiEvent
}