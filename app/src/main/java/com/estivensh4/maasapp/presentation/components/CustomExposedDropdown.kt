package com.estivensh4.maasapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.estivensh4.maasapp.presentation.ui.theme.LocalSpacing
import com.estivensh4.maasapp.presentation.ui.theme.MaasAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomExposedDropdown(
    label: String,
    options: List<T>,
    value: String,
    position: (Int) -> String,
    selectOptionChange: (T) -> Unit,
    errorMessage: String = "",
) {
    val localSpacing = LocalSpacing.current
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableIntStateOf(0) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth(),
    ) {
        CustomOutlinedTextField(
            label = label,
            value = value,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedTextColor = Color(0xFF3C3C3C),
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = Color(0xFF3C3C3C),
                cursorColor = MaterialTheme.colorScheme.tertiary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                disabledContainerColor = Color(0xFFB3B3B3),
                disabledTextColor = Color(0xFF757575),
                focusedLabelColor = Color(0xFF3C3C3C),
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorLabelColor = MaterialTheme.colorScheme.error,
                errorContainerColor = MaterialTheme.colorScheme.onSurface,
                errorTextColor = MaterialTheme.colorScheme.error,
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.tertiary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
            ),
            errorMessage = errorMessage,
            isError = false,
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .onSizeChanged { size -> textFieldSize = size.width },
            onValueChange = {},
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface)
                .width(with(LocalDensity.current) { textFieldSize.toDp() }),
        ) {
            options.forEachIndexed { index, _ ->
                DropdownMenuItem(
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    onClick = {
                        expanded = false
                        selectOptionChange(options[index])
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Color(0xFF3C3C3C),
                    ),
                    text = { Text(position.invoke(index)) }
                )
            }
        }
    }
}


@Preview
@Composable
fun CustomExposed() {
    val documentTypeList = remember {
        mutableStateListOf(
            "Cedula",
            "Extranjeria"
        )
    }
    var documentType by remember { mutableStateOf("") }
    MaasAppTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CustomExposedDropdown(
                label = "Tipo de documento",
                options = documentTypeList,
                value = documentType,
                position = { documentTypeList[it] },
                selectOptionChange = {
                    documentType = it
                }
            )
        }
    }
}