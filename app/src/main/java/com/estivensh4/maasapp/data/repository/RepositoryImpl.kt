package com.estivensh4.maasapp.data.repository

import com.estivensh4.maasapp.data.local.dao.UserDao
import com.estivensh4.maasapp.data.local.mappers.toEntity
import com.estivensh4.maasapp.data.local.mappers.toUser
import com.estivensh4.maasapp.domain.model.GetBalanceCardOutput
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.model.User
import com.estivensh4.maasapp.domain.model.ValidCardOutput
import com.estivensh4.maasapp.domain.presentation.Repository
import com.estivensh4.maasapp.util.CustomException
import com.estivensh4.maasapp.util.toLocalException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@OptIn(InternalAPI::class)
class RepositoryImpl(
    private val httpClient: HttpClient,
    private val userDao: UserDao
) : Repository {

    override suspend fun validCard(card: String): Result<ValidCardOutput> {
        return try {
            val result = httpClient.get("${ENDPOINT_VALID_CARD}/$card")
            if (result.status == HttpStatusCode.BadRequest) {
                val exception = result.content.toLocalException()
                Result.failure(
                    CustomException(
                        errorCode = exception?.errorCode.orEmpty(),
                        errorMessage = exception?.errorMessage.orEmpty(),
                    )
                )
            } else {
                Result.success(result.body())
            }
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