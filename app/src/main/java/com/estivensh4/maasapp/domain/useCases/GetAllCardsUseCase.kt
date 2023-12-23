package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.presentation.Repository

class GetAllCardsUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.getAllCards()
}