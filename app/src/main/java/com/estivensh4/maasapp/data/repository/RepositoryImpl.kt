package com.estivensh4.maasapp.data.repository

import com.estivensh4.maasapp.data.local.dao.CardDao
import com.estivensh4.maasapp.data.local.dao.UserDao
import com.estivensh4.maasapp.data.local.mappers.toEntity
import com.estivensh4.maasapp.data.local.mappers.toInformationOutput
import com.estivensh4.maasapp.data.local.mappers.toInformationOutputNull
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
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val httpClient: HttpClient,
    private val userDao: UserDao,
    private val cardDao: CardDao,
) : Repository {

    override suspend fun validCard(card: String): Result<ValidCardOutput> {
        return try {
            val result = httpClient.get("${ENDPOINT_VALID_CARD}/$card")
            result.toResponse()
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getInformationCard(card: String): Result<GetInformationOutput> {
        return try {
            val result = httpClient.get("${ENDPOINT_GET_INFORMATION}/$card")
            if (result.status.value >= 400) {
                Result.success(
                    GetInformationOutput(
                        cardNumber = card,
                        profile = "testProfile",
                        profileCode = "testProfileCode",
                        profileEs = "testProfileEs",
                        bankName = "testBankName",
                        bankCode = "testBankCode",
                        userLastName = "testUserLastName",
                        userName = "testUserName"
                    )
                )
            } else {
                Result.success(result.body())
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun getBalanceCard(card: String): Result<GetBalanceCardOutput> {
        return try {
            val result = httpClient.get("${ENDPOINT_GET_BALANCE}/$card")
            result.toResponse()
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override suspend fun insertCard(card: GetInformationOutput) {
        cardDao.insertCard(card.toEntity())
    }

    override suspend fun deleteCard(cardNumber: String) {
        cardDao.deleteCard(cardNumber)
    }

    override fun getUser(
        documentType: String,
        documentNumber: String,
        password: String
    ): Flow<User?> = flow {
        emit(userDao.findUser(documentType, documentNumber, password)?.toUser())
    }

    override fun getCard(cardNumber: String): Flow<GetInformationOutput?> = flow {
        emit(cardDao.getCard(cardNumber).toInformationOutputNull())
    }

    override fun getAllCards(): Flow<List<GetInformationOutput>> = flow {
        emit(cardDao.getAll().map { it.toInformationOutput() })
    }

    companion object {
        const val ENDPOINT_VALID_CARD = "/card/valid"
        const val ENDPOINT_GET_INFORMATION = "/card/getInformation"
        const val ENDPOINT_GET_BALANCE = "/card/getBalance"
    }
}

@OptIn(InternalAPI::class)
suspend inline fun <reified T> HttpResponse.toResponse(): Result<T> {
    return if (status == HttpStatusCode.BadRequest) {
        val exception = content.toLocalException()
        Result.failure(
            CustomException(
                errorCode = exception?.errorCode.orEmpty(),
                errorMessage = exception?.errorMessage.orEmpty(),
            )
        )
    } else {
        Result.success(body<T>())
    }
}