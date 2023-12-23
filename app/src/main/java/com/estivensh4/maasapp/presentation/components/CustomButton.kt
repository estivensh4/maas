package com.estivensh4.maasapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val gradient = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primaryContainer
        )
    )
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .background(gradient, CircleShape)
            .then(modifier),
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = text,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun CustomButtonPrev() {
    MaasAppTheme {
        CustomButton(text = "Continuar") {

        }
    }
}