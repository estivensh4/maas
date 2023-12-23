package com.estivensh4.maasapp.di

import com.estivensh4.maasapp.presentation.viewmodel.DashboardViewModel
import com.estivensh4.maasapp.presentation.viewmodel.FormViewModel
import com.estivensh4.maasapp.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { FormViewModel(get()) }
    single { DashboardViewModel(get()) }
}