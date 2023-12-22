package com.estivensh4.maasapp.data.repository

import com.estivensh4.maasapp.data.local.dao.UserDao
import com.estivensh4.maasapp.data.local.mappers.toEntity
import com.estivensh4.maasapp.data.local.mappers.toUser
import com.estivensh4.maasapp.domain.model.GetBalanceCardOutput
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.model.User
import com.estivensh4.maasapp.domain.model.ValidCardOutput
import com.estivensh4.maasapp.domain.presentation.Repository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.w3c.dom.DocumentType

class RepositoryImpl(
    private val httpClient: HttpClient,
    private val userDao: UserDao
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

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override fun getUser(
        documentType: String,
        documentNumber: String,
        password: String
    ): Flow<User?> = flow {
        emit(userDao.findUser(documentType, documentNumber, password)?.toUser())
    }


    companion object {
        const val ENDPOINT_VALID_CARD = "/card/valid"
        const val ENDPOINT_GET_INFORMATION = "/card/getInformation"
        const val ENDPOINT_GET_BALANCE = "/card/getBalance"
    }
}