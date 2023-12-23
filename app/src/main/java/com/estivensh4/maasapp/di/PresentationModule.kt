package com.estivensh4.maasapp.di

import com.estivensh4.maasapp.presentation.viewmodel.DashboardViewModel
import com.estivensh4.maasapp.presentation.viewmodel.FormViewModel
import com.estivensh4.maasapp.presentation.viewmodel.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { LoginViewModel(get(), androidContext()) }
    viewModel { FormViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
}