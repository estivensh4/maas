package com.estivensh4.maasapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.estivensh4.maasapp.presentation.components.CustomButton
import com.estivensh4.maasapp.presentation.components.CustomOutlinedTextField
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme

@Composable
fun LoginScreen(navController: NavController) {
    var documentType by remember { mutableStateOf("") }
    var documentNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CustomOutlinedTextField(
            value = documentType,
            onValueChange = { documentType = it },
            label = "Tipo de documento"
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = documentNumber,
            onValueChange = { documentNumber = it },
            label = "Documento"
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.size(8.dp))
        CustomButton(
            text = "Continuar",
            modifier = Modifier.fillMaxWidth()
        ) {
            
        }
    }
}

@Preview
@Composable
fun LoginScreenPrev() {
    MaasAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}