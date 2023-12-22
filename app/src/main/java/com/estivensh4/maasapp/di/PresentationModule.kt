package com.estivensh4.maasapp.di

import com.estivensh4.maasapp.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { LoginViewModel() }
}