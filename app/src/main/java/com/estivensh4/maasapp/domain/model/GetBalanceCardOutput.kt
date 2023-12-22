package com.estivensh4.maasapp.domain.model

data class GetBalanceCardOutput(
    val card: String,
    val balance: Int,
    val balanceDate: String,
    val virtualBalance: Int,
    val virtualBalanceDate: String
)
