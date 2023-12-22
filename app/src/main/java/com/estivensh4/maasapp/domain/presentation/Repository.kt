package com.estivensh4.maasapp.domain.presentation

import com.estivensh4.maasapp.domain.model.GetBalanceCardOutput
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.model.User
import com.estivensh4.maasapp.domain.model.ValidCardOutput
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun validCard(card: String): Result<ValidCardOutput>
    suspend fun getInformationCard(card: String): Result<GetInformationOutput>
    suspend fun getBalanceCard(card: String): Result<GetBalanceCardOutput>
    suspend fun insertUser(user: User)
    fun getUser(documentType: String, documentNumber: String, password: String): Flow<User?>
}