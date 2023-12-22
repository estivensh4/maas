package com.estivensh4.maasapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GetBalanceCardOutput(
    val card: String,
    val balance: Int,
    val balanceDate: String,
    val virtualBalance: Int,
    val virtualBalanceDate: String?
)
