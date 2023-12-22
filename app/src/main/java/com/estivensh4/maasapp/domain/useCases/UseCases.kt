package com.estivensh4.maasapp.domain.useCases

data class UseCases(
    val getBalanceCardUseCase: GetBalanceCardUseCase,
    val getInformationCardUseCase: GetInformationCardUseCase,
    val validCardUseCase: ValidCardUseCase,
    val insertUserUseCase: InsertUserUseCase,
    val getUserUseCase: GetUserUseCase
)