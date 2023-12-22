package com.estivensh4.maasapp.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    regex: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(12.dp)
    Column {
        Card(
            shape = shape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    if (regex.isNotEmpty()) {
                        if (value.contains(Regex(regex))) onValueChange(value)
                    } else {
                        onValueChange(value)
                    }
                },
                modifier = modifier,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFB3B3B3),
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    focusedTextColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = Color(0xFF3C3C3C),
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                    errorCursorColor = MaterialTheme.colorScheme.error,
                    disabledContainerColor = Color(0xFFB3B3B3),
                    disabledBorderColor = Color(0xFF757575),
                    disabledTextColor = Color(0xFF757575),
                ),
                shape = shape,
                enabled = enabled
            )
        }
        AnimatedVisibility(visible = isError) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomOutlinedTextFieldPrev() {
    var text by rememberSaveable { mutableStateOf("seesse") }
    MaasAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center) {
                CustomOutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    isError = false,
                    errorMessage = "Error message",
                    enabled = true
                )
            }
        }
    }
}