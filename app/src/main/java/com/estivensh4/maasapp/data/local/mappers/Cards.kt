package com.estivensh4.maasapp.data.local.mappers

import com.estivensh4.maasapp.data.local.entity.CardEntity
import com.estivensh4.maasapp.domain.model.GetInformationOutput

fun CardEntity.toInformationOutput(): GetInformationOutput {
    return GetInformationOutput(
        cardNumber = cardNumber,
        profileCode = profileCode,
        profile = profile,
        profileEs = profileEs,
        bankCode = bankCode,
        bankName = bankName,
        userName = userName,
        userLastName = userLastName
    )
}

fun CardEntity?.toInformationOutputNull(): GetInformationOutput? {
    return if (this != null){
        GetInformationOutput(
            cardNumber = cardNumber,
            profileCode = profileCode,
            profile = profile,
            profileEs = profileEs,
            bankCode = bankCode,
            bankName = bankName,
            userName = userName,
            userLastName = userLastName
        )
    } else null
}

fun GetInformationOutput.toEntity(): CardEntity {
    return CardEntity(
        cardNumber = cardNumber,
        profileCode = profileCode,
        profile = profile,
        profileEs = profileEs,
        bankCode = bankCode,
        bankName = bankName,
        userName = userName,
        userLastName = userLastName
    )
}