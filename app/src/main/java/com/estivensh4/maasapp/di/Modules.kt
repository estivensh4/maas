package com.estivensh4.maasapp.di

import org.koin.dsl.module

val appModules = listOf(
    dataModule,
    domainModule,
    presentationModule
)