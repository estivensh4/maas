package com.estivensh4.maasapp.data.local.mappers

import com.estivensh4.maasapp.data.local.entity.UserEntity
import com.estivensh4.maasapp.domain.model.User

fun UserEntity?.toUser(): User? {
    return if (this != null){
        User(
            documentNumber = documentNumber,
            documentType = documentType,
            password = password,
            fullName = fullName,
            address = address,
            email = email
        )
    } else null
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        documentNumber = documentNumber,
        documentType = documentType,
        password = password,
        fullName = fullName,
        address = address,
        email = email,
    )
}