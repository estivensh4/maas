package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.presentation.Repository

class GetCardUseCase(
    private val repository: Repository
) {
    operator fun invoke(cardNumber: String) = repository.getCard(cardNumber)
}