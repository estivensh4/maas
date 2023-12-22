package com.estivensh4.maasapp.data.repository

import com.estivensh4.maasapp.domain.model.GetBalanceCardOutput
import com.estivensh4.maasapp.domain.model.GetInformationOutput
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
                                card = "1010000008551426",
                                isValid = true,
                                status = "Enable",
                                statusCode = 1
                            )
                        )
                    }
                }
            }
        }
        val response = repository.validCard("1010000008551426")
        assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun `getBalance, valid response, returns success`() = testApplication {

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
                    get("card/getBalance/1010000008551426") {
                        call.respond(
                            GetBalanceCardOutput(
                                card = "1010000008551426",
                                balance = 3500,
                                balanceDate = "2021-08-31 09:18:58",
                                virtualBalance = 0,
                                virtualBalanceDate = null
                            )
                        )
                    }
                }
            }
        }
        val response = repository.getBalanceCard("1010000008551426")
        assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun `getInformation, valid response, returns success`() = testApplication {

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
                    get("card/getInformation/1010000008551426") {
                        call.respond(
                            GetInformationOutput(
                                cardNumber = "1010000008551426",
                                profile = "e",
                                profileCode = "",
                                profileEs = "",
                                bankCode = "",
                                bankName = "",
                                userLastName = "Estiven",
                                userName = "estivensh4"
                            )
                        )
                    }
                }
            }
        }
        val response = repository.getInformationCard("1010000008551426")
        assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun `getInformation, invalid response, returns failure`() = testApplication {

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
                    get("card/getInformation/1010000008551426") {
                        call.respond(
                            GetBalanceCardOutput(
                                card = "sjejse",
                                balanceDate = "1",
                                balance = 0,
                                virtualBalanceDate = "1",
                                virtualBalance = 0
                            )
                        )
                    }
                }
            }
        }
        val response = repository.getInformationCard("1010000008551426")
        assertThat(response.isFailure).isTrue()
    }

    @Test
    fun `getBalanceCard, invalid response, returns failure`() = testApplication {

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
                    get("card/getBalance/1010000008551426") {
                        call.respond(
                            GetInformationOutput(
                                cardNumber = "1010000008551426",
                                profile = "e",
                                profileCode = "",
                                profileEs = "",
                                bankCode = "",
                                bankName = "",
                                userLastName = "Estiven",
                                userName = "estivensh4"
                            )
                        )
                    }
                }
            }
        }
        val response = repository.getBalanceCard("1010000008551426")
        assertThat(response.isFailure).isTrue()
    }

    @Test
    fun `validCard, invalid response, returns failure`() = testApplication {

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
                    get("card/validCard/1010000008551426") {
                        call.respond(
                            GetInformationOutput(
                                cardNumber = "1010000008551426",
                                profile = "e",
                                profileCode = "",
                                profileEs = "",
                                bankCode = "",
                                bankName = "",
                                userLastName = "Estiven",
                                userName = "estivensh4"
                            )
                        )
                    }
                }
            }
        }
        val response = repository.validCard("1010000008551426")
        assertThat(response.isFailure).isTrue()
    }
}