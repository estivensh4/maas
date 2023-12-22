package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.presentation.Repository

class GetBalanceCardUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(card: String) = repository.getBalanceCard(card)
}