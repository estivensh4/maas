package com.estivensh4.maasapp.domain.useCases

import com.estivensh4.maasapp.domain.model.User
import com.estivensh4.maasapp.domain.presentation.Repository

class InsertUserUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(user: User) = repository.insertUser(user)
}