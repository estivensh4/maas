package com.estivensh4.maasapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.estivensh4.maasapp.presentation.components.CustomButton
import com.estivensh4.maasapp.presentation.components.CustomExposedDropdown
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
    val documentTypeList by loginViewModel.documentTypeList.collectAsState()

    LaunchedEffect(true) {
        loginViewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> navController.navigate(uiEvent.screen)
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
        CustomExposedDropdown(
            label = "Tipo de documento",
            options = documentTypeList,
            value = documentType,
            position = { documentTypeList[it] },
            selectOptionChange = loginViewModel::setDocumentType
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = documentNumber,
            onValueChange = loginViewModel::setDocumentNumber,
            label = "Documento",
            errorMessage = "El numero de documento es invalido",
            inputType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = password,
            onValueChange = loginViewModel::setPassword,
            label = "Contraseña",
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = "La contraseña debe tener minimo 8 digitos",
            inputType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomButton(
            text = "Continuar",
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            onClick = {
                loginViewModel.onEvent(LoginViewModel.LoginEvents.ValidateForm)
            }
        )
    }
}

@Preview
@Composable
fun LoginScreenPrev() {
    MaasAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}