package com.estivensh4.maasapp.data.repository

import com.estivensh4.maasapp.domain.model.GetBalanceCardOutput
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.model.ValidCardOutput
import com.estivensh4.maasapp.domain.presentation.Repository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RepositoryImpl(
    private val httpClient: HttpClient
) : Repository {

    override suspend fun validCard(card: String): Result<ValidCardOutput> {
        return try {
            val result = httpClient.get("${ENDPOINT_VALID_CARD}/$card")
            Result.success(result.body())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getInformationCard(card: String): Result<GetInformationOutput> {
        return try {
            val result = httpClient.get("${ENDPOINT_GET_INFORMATION}/$card")
            Result.success(result.body())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getBalanceCard(card: String): Result<GetBalanceCardOutput> {
        return try {
            val result = httpClient.get("${ENDPOINT_GET_BALANCE}/$card")
            Result.success(result.body())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    companion object {
        const val ENDPOINT_VALID_CARD = "/card/valid"
        const val ENDPOINT_GET_INFORMATION = "/card/getInformation"
        const val ENDPOINT_GET_BALANCE = "/card/getBalance"
    }
}