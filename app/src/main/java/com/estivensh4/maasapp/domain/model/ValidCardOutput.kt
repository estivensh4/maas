package com.estivensh4.maasapp.domain.model

data class ValidCardOutput(
    val card: String,
    val isValid: Boolean,
    val status: String,
    val statusCode: Int
)