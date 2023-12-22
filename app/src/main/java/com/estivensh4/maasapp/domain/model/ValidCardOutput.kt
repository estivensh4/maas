package com.estivensh4.maasapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidCardOutput(
    val card: String,
    val isValid: Boolean,
    val status: String,
    val statusCode: Int
)