package com.estivensh4.maasapp.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    regex: String = "",
    isError: Boolean = false,
    readOnly: Boolean = false,
    errorMessage: String = "",
    enabled: Boolean = true,
    label: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    inputType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.tertiary,
        unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = Color(0xFF3C3C3C),
        cursorColor = MaterialTheme.colorScheme.tertiary,
        errorCursorColor = MaterialTheme.colorScheme.error,
        disabledContainerColor = Color(0xFFB3B3B3),
        disabledTextColor = Color(0xFF757575),
        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorContainerColor = MaterialTheme.colorScheme.onSurface,
        errorTextColor = MaterialTheme.colorScheme.error,
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
    )
) {
    val shape = RoundedCornerShape(12.dp)
    var focusEnabled by remember { mutableStateOf(false) }
    Column {
        Card(
            shape = shape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            TextField(
                value = value,
                label = {
                    Text(
                        text = label,
                        style = if (!focusEnabled) {
                            if (value.isEmpty()) {
                                MaterialTheme.typography.titleMedium
                            } else MaterialTheme.typography.labelMedium
                        } else {
                            MaterialTheme.typography.labelMedium
                        }
                    )
                },
                onValueChange = { newValue ->
                    if (regex.isNotEmpty()) {
                        if (newValue.contains(Regex(regex))) onValueChange(newValue)
                    } else {
                        onValueChange(newValue)
                    }
                },
                singleLine = true,
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusEnabled = it.isFocused },
                colors = colors,
                shape = shape,
                enabled = enabled,
                visualTransformation = visualTransformation,
                isError = isError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = inputType,
                    imeAction = imeAction
                ),
                trailingIcon = trailingIcon,
                readOnly = readOnly
            )
        }
        AnimatedVisibility(visible = isError) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomOutlinedTextFieldPrev() {
    var text by rememberSaveable { mutableStateOf("") }
    MaasAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center) {
                CustomOutlinedTextField(
                    label = "Clave",
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