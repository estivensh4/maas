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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.estivensh4.maasapp.R
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val gradientEnabled = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primaryContainer
        )
    )

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        modifier = Modifier
            .then(if (enabled) Modifier.background(gradientEnabled, CircleShape) else Modifier)
            .then(if (!enabled) Modifier.background(Color(0xFFdddcdc), CircleShape) else Modifier)
            .then(modifier),
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = if (enabled) Color.White else Color(0xFF909190)
            )
        }
    }
}

@Preview
@Composable
fun CustomButtonPrev() {
    MaasAppTheme {
        CustomButton(text = stringResource(id = R.string.btn_continue), enabled = false) {

        }
    }
}