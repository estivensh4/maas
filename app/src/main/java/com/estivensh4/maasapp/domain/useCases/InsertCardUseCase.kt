package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.presentation.Repository

class InsertCardUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(card: GetInformationOutput) = repository.insertCard(card)
}