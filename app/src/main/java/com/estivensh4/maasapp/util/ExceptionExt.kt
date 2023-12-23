package com.estivensh4.maasapp.util

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

@Serializable
data class CustomException(
    val errorCode: String = "",
    val errorMessage: String = ""
) : Exception()

suspend fun ByteReadChannel?.toLocalException(): CustomException? {
    val exception = this?.readUTF8Line()?.let {
        json.decodeFromString<CustomException>(it)
    }
    return exception
}

suspend fun Throwable.getException(): CustomException {
    return try {
        when (this) {
            is ClientRequestException -> response.body()
            is ServerResponseException -> response.body()
            is CustomException -> {
                CustomException(
                    errorMessage = errorMessage,
                    errorCode = errorCode
                )
            }

            else -> CustomException(
                errorMessage = message.orEmpty(),
                errorCode = message.orEmpty()
            )
        }
    } catch (exception: Exception) {
        CustomException(
            errorMessage = message.orEmpty(),
            errorCode = message.orEmpty()
        )
    }
}