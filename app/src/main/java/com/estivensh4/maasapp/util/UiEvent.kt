package com.estivensh4.maasapp.util

import com.estivensh4.maasapp.domain.model.Screen

sealed interface UiEvent {
    data class ShowMessage(val message: String): UiEvent
    data class Navigate(val screen: String): UiEvent
    data object Nothing: UiEvent
}