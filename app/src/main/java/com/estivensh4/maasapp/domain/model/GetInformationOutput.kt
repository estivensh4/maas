package com.estivensh4.maasapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetInformationOutput(
    val cardNumber: String,
    val profileCode: String,
    val profile: String,
    @SerialName("profile_es") val profileEs: String,
    val bankCode: String,
    val bankName: String,
    val userName: String,
    val userLastName: String
)
