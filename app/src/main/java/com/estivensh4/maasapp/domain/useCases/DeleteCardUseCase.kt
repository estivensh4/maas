package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.presentation.Repository

class DeleteCardUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(cardNumber: String) = repository.deleteCard(cardNumber)
}