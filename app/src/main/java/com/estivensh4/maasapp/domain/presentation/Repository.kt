package com.estivensh4.maasapp.domain.presentation

import com.estivensh4.maasapp.domain.model.GetBalanceCardOutput
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.model.ValidCardOutput

interface Repository {
    suspend fun validCard(card: String): Result<ValidCardOutput>
    suspend fun getInformationCard(card: String): Result<GetInformationOutput>
    suspend fun getBalanceCard(card: String): Result<GetBalanceCardOutput>
}