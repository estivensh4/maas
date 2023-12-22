package com.estivensh4.maasapp.di

import com.estivensh4.maasapp.domain.presentation.Repository
import com.estivensh4.maasapp.domain.useCases.GetBalanceCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetInformationCardUseCase
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
        validCardUseCase = ValidCardUseCase(repository)
    )
}