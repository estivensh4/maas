package com.estivensh4.maasapp.data.repository

import com.estivensh4.maasapp.domain.model.ValidCardOutput
import com.estivensh4.maasapp.domain.presentation.Repository
import com.google.common.truth.Truth.assertThat
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class RepositoryImplTest : KoinTest {

    private val repository: Repository by inject()

    @Test
    fun `validateCard, valid response, returns success`() = testApplication {

        startKoin {
            modules(
                module {
                    single {
                        createClient {
                            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                                json()
                            }
                            defaultRequest {
                                url("https://osgqhdx2wf.execute-api.us-west-2.amazonaws.com")
                            }
                        }
                    }
                    single<Repository> { RepositoryImpl(get()) }
                }
            )
        }

        externalServices {
            hosts("https://osgqhdx2wf.execute-api.us-west-2.amazonaws.com") {
                install(ContentNegotiation) {
                    json()
                }
                routing {
                    get("card/valid/1010000008551426") {
                        call.respond(
                            ValidCardOutput(
                                card = "1234567890",
                                isValid = true,
                                status = "OK",
                                statusCode = 0
                            )
                        )
                    }
                }
            }
        }
        val response = repository.validCard("1010000008551426")
        assertThat(response.isSuccess).isTrue()
    }

    /*@Test
    fun `validateCard, invalid response, returns failure`() = testApplication {

        startKoin {
            modules(
                module {
                    single {
                        createClient {
                            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                                json()
                            }
                            defaultRequest {
                                url("https://osgqhdx2wf.execute-api.us-west-2.amazonaws.com")
                            }
                        }
                    }
                    single<Repository> { RepositoryImpl(get()) }
                }
            )
        }


        externalServices {
            hosts("https://osgqhdx2wf.execute-api.us-west-2.amazonaws.com") {
                install(ContentNegotiation) {
                    json()
                }
                routing {
                    get("card/valid/") {
                        call.respond(
                            message = ValidCardOutput(
                                card = "1234567890",
                                isValid = true,
                                status = "OK",
                                statusCode = 0
                            ),
                            status = HttpStatusCode.BadRequest

                        )
                    }
                }
            }
        }
        val response = repository.validCard("1010000008551426")
        assertThat(response.isFailure).isTrue()
    }*/
}