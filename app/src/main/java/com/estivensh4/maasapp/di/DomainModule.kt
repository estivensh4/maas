package com.estivensh4.maasapp.di

import com.estivensh4.maasapp.domain.presentation.Repository
import com.estivensh4.maasapp.domain.useCases.DeleteCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetAllCardsUseCase
import com.estivensh4.maasapp.domain.useCases.GetBalanceCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetInformationCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetUserUseCase
import com.estivensh4.maasapp.domain.useCases.InsertCardUseCase
import com.estivensh4.maasapp.domain.useCases.InsertUserUseCase
import com.estivensh4.maasapp.domain.useCases.UseCases
import com.estivensh4.maasapp.domain.useCases.ValidCardUseCase
import org.koin.dsl.module

val domainModule = module {
    single { provideUseCases(get()) }
}

fun provideUseCases(repository: Repository): UseCases {
    return UseCases(
        getBalanceCardUseCase = GetBalanceCardUseCase(repository),
        getInformationCardUseCase = GetInformationCardUseCase(repository),
        validCardUseCase = ValidCardUseCase(repository),
        insertUserUseCase = InsertUserUseCase(repository),
        getUserUseCase = GetUserUseCase(repository),
        getAllCardsUseCase = GetAllCardsUseCase(repository),
        insertCardUseCase = InsertCardUseCase(repository),
        deleteCardUseCase = DeleteCardUseCase(repository),
        getCardUseCase = GetCardUseCase(repository)
    )
}