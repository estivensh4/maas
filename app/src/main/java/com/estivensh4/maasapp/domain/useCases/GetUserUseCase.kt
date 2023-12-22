package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.presentation.Repository

class GetUserUseCase(
    private val repository: Repository
) {
    operator fun invoke(
        documentType: String,
        documentNumber: String,
        password: String
    ) = repository.getUser(documentType, documentNumber, password)
}