package com.estivensh4.maasapp.di

import com.estivensh4.maasapp.BuildConfig
import com.estivensh4.maasapp.data.repository.RepositoryImpl
import com.estivensh4.maasapp.domain.presentation.Repository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single<Repository> { RepositoryImpl(get()) }
    single { provideHttpClient() }
}

fun provideHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            headers {
                url("https://osgqhdx2wf.execute-api.us-west-2.amazonaws.com/")
                append(HttpHeaders.Authorization, "Bearer ${BuildConfig.API_TOKEN}")
            }
        }

        install(Logging) {
            level = LogLevel.ALL
        }
    }
}