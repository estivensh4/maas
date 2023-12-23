package com.estivensh4.maasapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.estivensh4.maasapp.R
import com.estivensh4.maasapp.domain.model.Screen
import com.estivensh4.maasapp.presentation.components.CustomButton
import com.estivensh4.maasapp.presentation.components.CustomExposedDropdown
import com.estivensh4.maasapp.presentation.components.CustomLoading
import com.estivensh4.maasapp.presentation.components.CustomOutlinedTextField
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme
import com.estivensh4.maasapp.presentation.viewmodel.LoginViewModel
import com.estivensh4.maasapp.util.UiEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val documentType by loginViewModel.documentType.collectAsState()
    val documentNumber by loginViewModel.documentNumber.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val enabled by loginViewModel.enabled.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val documentTypeList by loginViewModel.documentTypeList.collectAsState()
    val isErrorDocumentNumber by loginViewModel.isErrorDocumentNumber.collectAsState()
    val isErrorPassword by loginViewModel.isErrorPassword.collectAsState()

    LaunchedEffect(true) {
        loginViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> navController.navigate(uiEvent.screen) {
                    popUpTo(Screen.LOGIN.name) { inclusive = true }
                }

                is UiEvent.ShowMessage -> {

                }

                else -> println("Nothing event")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.text_login_or_register),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = stringResource(id = R.string.description_login_or_register),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(24.dp))
        CustomExposedDropdown(
            label = stringResource(id = R.string.lbl_document_type),
            options = documentTypeList,
            value = documentType,
            position = { documentTypeList[it] },
            selectOptionChange = loginViewModel::setDocumentType
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = documentNumber,
            onValueChange = loginViewModel::setDocumentNumber,
            label = stringResource(id = R.string.lbl_document_number),
            errorMessage = stringResource(id = R.string.text_error_document_number),
            inputType = KeyboardType.Number,
            isError = isErrorDocumentNumber,
            testTag = stringResource(id = R.string.test_tag_text_field_document_number)
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = password,
            onValueChange = loginViewModel::setPassword,
            label = stringResource(id = R.string.lbl_password),
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = stringResource(id = R.string.text_error_password),
            inputType = KeyboardType.Password,
            isError = isErrorPassword,
            testTag = stringResource(id = R.string.test_tag_text_field_password),
            imeAction = ImeAction.Send,
            keyboardActions = KeyboardActions(
                onSend = {
                    if (enabled) {
                        loginViewModel.onEvent(LoginViewModel.LoginEvents.ValidateUser)
                    }
                }
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomButton(
            text = stringResource(id = R.string.btn_continue),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            testTag = stringResource(id = R.string.test_tag_btn_continue),
            onClick = {
                loginViewModel.onEvent(LoginViewModel.LoginEvents.ValidateUser)
            }
        )
    }
    CustomLoading(showProgress = isLoading)
}

@Preview
@Composable
fun LoginScreenPrev() {
    MaasAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}