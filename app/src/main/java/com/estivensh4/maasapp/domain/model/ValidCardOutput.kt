package com.estivensh4.maasapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidCardOutput(
    @SerialName("card") val card: String,
    @SerialName("isValid") val isValid: Boolean,
    @SerialName("status") val status: String,
    @SerialName("statusCode") val statusCode: Int
)