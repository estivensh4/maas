package com.estivensh4.maasapp

import android.content.Context
import android.os.Bundle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.estivensh4.maasapp.domain.model.Screen
import com.estivensh4.maasapp.domain.useCases.DeleteCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetAllCardsUseCase
import com.estivensh4.maasapp.domain.useCases.GetBalanceCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetInformationCardUseCase
import com.estivensh4.maasapp.domain.useCases.GetUserUseCase
import com.estivensh4.maasapp.domain.useCases.InsertCardUseCase
import com.estivensh4.maasapp.domain.useCases.InsertUserUseCase
import com.estivensh4.maasapp.domain.useCases.UseCases
import com.estivensh4.maasapp.domain.useCases.ValidCardUseCase
import com.estivensh4.maasapp.presentation.screens.FormScreen
import com.estivensh4.maasapp.presentation.screens.LoginScreen
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme
import com.estivensh4.maasapp.presentation.viewmodel.LoginViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenE2E {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var repositoryFake: RepositoryFake
    private lateinit var useCases: UseCases
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var navController: NavHostController
    private lateinit var testTagDocumentNumber: String
    private lateinit var testTagPassword: String
    private lateinit var testTagBtnContinue: String
    private lateinit var context: Context

    @Before
    fun setUp() {
        repositoryFake = RepositoryFake()
        context = InstrumentationRegistry.getInstrumentation().context
        useCases = UseCases(
            getBalanceCardUseCase = GetBalanceCardUseCase(repositoryFake),
            getInformationCardUseCase = GetInformationCardUseCase(repositoryFake),
            getCardUseCase = GetCardUseCase(repositoryFake),
            validCardUseCase = ValidCardUseCase(repositoryFake),
            getAllCardsUseCase = GetAllCardsUseCase(repositoryFake),
            insertUserUseCase = InsertUserUseCase(repositoryFake),
            insertCardUseCase = InsertCardUseCase(repositoryFake),
            getUserUseCase = GetUserUseCase(repositoryFake),
            deleteCardUseCase = DeleteCardUseCase(repositoryFake)
        )
        loginViewModel = LoginViewModel(
            useCases = useCases,
            context = context
        )
        composeRule.setContent {
            MaasAppTheme {
                navController = rememberNavController()
                testTagDocumentNumber = stringResource(id = R.string.test_tag_text_field_document_number)
                testTagPassword = stringResource(id = R.string.test_tag_text_field_password)
                testTagBtnContinue = stringResource(id = R.string.test_tag_btn_continue)
                NavHost(
                    navController = navController,
                    startDestination = Screen.LOGIN.name
                ) {
                    composable(Screen.LOGIN.name) {
                        LoginScreen(
                            navController = navController
                        )
                    }
                    composable(
                        "${Screen.FORM.name}?documentType={documentType}&" +
                                "documentNumber={documentNumber}&" +
                                "password={password}"
                    ) {
                        val bundle = it.arguments ?: Bundle()
                        val documentType = bundle.getString("documentType").orEmpty()
                        val documentNumber = bundle.getString("documentNumber").orEmpty()
                        val password = bundle.getString("password").orEmpty()
                        FormScreen(
                            navController = navController,
                            documentType = documentType,
                            documentNumber = documentNumber,
                            password = password
                        )
                    }
                }
            }
        }
    }

    @Test
    fun enterTextDocumentNumber_And_Password_And_ClickBtnContinue(): Unit =
        runBlocking {
            val documentNumber = "1234567890"
            val password = "12345678"

            assertThat(
                navController
                    .currentDestination
                    ?.route
                    ?.startsWith(Screen.LOGIN.name)
            ).isTrue()
            composeRule.onRoot().printToLog("TAG")
            composeRule.onNode(
                hasTestTag(testTagDocumentNumber)
            ).assertIsDisplayed()

            composeRule.onNode(
                hasTestTag(testTagDocumentNumber)
            ).performTextInput(documentNumber)

            composeRule.onNode(
                hasTestTag(testTagDocumentNumber)
            ).assert(hasText(documentNumber))

            composeRule.onNode(
                hasTestTag(testTagDocumentNumber)
            ).performImeAction()

            composeRule.onNode(
                hasTestTag(testTagPassword)
            ).assertIsDisplayed()

            composeRule.onNode(
                hasTestTag(testTagPassword)
            ).performTextInput(password)

            composeRule.onNode(
                hasTestTag(testTagBtnContinue)
            ).assertIsDisplayed()

            composeRule.onNode(
                hasTestTag(testTagBtnContinue)
            ).assertIsEnabled()

            composeRule.onNode(
                hasTestTag(testTagBtnContinue)
            ).performClick()

            assertThat(
                navController
                    .currentDestination
                    ?.route
                    ?.startsWith(Screen.FORM.name)
            )
        }
}
