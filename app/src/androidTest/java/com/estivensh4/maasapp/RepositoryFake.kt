package com.estivensh4.maasapp

import com.estivensh4.maasapp.domain.model.GetBalanceCardOutput
import com.estivensh4.maasapp.domain.model.GetInformationOutput
import com.estivensh4.maasapp.domain.model.User
import com.estivensh4.maasapp.domain.model.ValidCardOutput
import com.estivensh4.maasapp.domain.presentation.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryFake : Repository {

    var shouldReturnError = false
    var validCardOutputFake = ValidCardOutput(
        card = "1010000008551426",
        isValid = true,
        status = "Enable",
        statusCode = 1
    )

    var getInformationOutputFake = GetInformationOutput(
        cardNumber = "1010000008551426",
        profile = "testProfile",
        profileCode = "testProfileCode",
        profileEs = "testProfileEs",
        bankName = "testBankName",
        bankCode = "testBankCode",
        userLastName = "testUserLastName",
        userName = "testUserName"
    )

    var getBalanceCardOutputFake = GetBalanceCardOutput(
        card = "1010000008551426",
        balance = 0,
        balanceDate = "",
        virtualBalance = 0,
        virtualBalanceDate = null
    )

    var userFake = User(
        documentType = "CC",
        documentNumber = "111111111",
        fullName = "Test",
        address = "esesse",
        password = "12345678",
        email = "eses"
    )

    override suspend fun validCard(card: String): Result<ValidCardOutput> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(validCardOutputFake)
        }

    }

    override suspend fun getInformationCard(card: String): Result<GetInformationOutput> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(getInformationOutputFake)
        }

    }

    override suspend fun getBalanceCard(card: String): Result<GetBalanceCardOutput> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(getBalanceCardOutputFake)
        }

    }

    override suspend fun insertUser(user: User) {
        //
    }

    override suspend fun insertCard(card: GetInformationOutput) {
        //
    }

    override suspend fun deleteCard(cardNumber: String) {
        //
    }

    override fun getUser(
        documentType: String,
        documentNumber: String,
        password: String
    ): Flow<User?> = flow {
        emit(userFake)
    }

    override fun getCard(cardNumber: String): Flow<GetInformationOutput?> = flow {
        emit(getInformationOutputFake)
    }

    override fun getAllCards(): Flow<List<GetInformationOutput>> = flow {
        emit(listOf(getInformationOutputFake))
    }
}
