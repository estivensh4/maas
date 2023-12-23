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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.estivensh4.maasapp.R
import com.estivensh4.maasapp.domain.model.Screen
import com.estivensh4.maasapp.presentation.components.CustomButton
import com.estivensh4.maasapp.presentation.components.CustomLoading
import com.estivensh4.maasapp.presentation.components.CustomOutlinedTextField
import com.estivensh4.maasapp.presentation.viewmodel.FormViewModel
import com.estivensh4.maasapp.util.UiEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun FormScreen(
    navController: NavController,
    formViewModel: FormViewModel = koinViewModel(),
    password: String,
    documentNumber: String,
    documentType: String
) {
    val fullName by formViewModel.fullName.collectAsState()
    val address by formViewModel.address.collectAsState()
    val email by formViewModel.email.collectAsState()
    val isLoading by formViewModel.isLoading.collectAsState()
    val enabled by formViewModel.enabled.collectAsState()
    val isErrorFullName by formViewModel.isErrorFullName.collectAsState()
    val isErrorAddress by formViewModel.isErrorAddress.collectAsState()
    val isErrorEmail by formViewModel.isErrorEmail.collectAsState()

    LaunchedEffect(true) {
        formViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> navController.navigate(event.screen) {
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
        CustomOutlinedTextField(
            value = fullName,
            onValueChange = formViewModel::setFullName,
            label = stringResource(id = R.string.lbl_full_name),
            errorMessage = stringResource(id = R.string.text_error_full_name),
            isError = isErrorFullName
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = address,
            onValueChange = formViewModel::setAddress,
            label = stringResource(id = R.string.lbl_address),
            errorMessage = stringResource(id = R.string.text_error_address),
            isError = isErrorAddress
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = email,
            onValueChange = formViewModel::setEmail,
            label = stringResource(id = R.string.lbl_email),
            errorMessage = stringResource(id = R.string.text_error_email),
            isError = isErrorEmail
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomButton(
            text = stringResource(id = R.string.btn_register),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            onClick = {
                formViewModel.onEvent(
                    FormViewModel.FormEvents.RegisterUser(
                        documentType = documentType,
                        documentNumber = documentNumber,
                        password = password
                    )
                )
            }
        )
    }
    CustomLoading(showProgress = isLoading)
}