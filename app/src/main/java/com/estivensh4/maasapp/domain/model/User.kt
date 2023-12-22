package com.estivensh4.maasapp.domain.model

data class User(
    val documentType: String,
    val documentNumber: String,
    val password: String,
    val fullName: String,
    val address: String,
    val email: String
)
