package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.presentation.Repository

class ValidCardUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(card: String) = repository.validCard(card)
}