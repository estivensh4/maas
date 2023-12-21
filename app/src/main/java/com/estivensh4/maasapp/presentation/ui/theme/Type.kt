package com.estivensh4.maasapp.presentation.ui.theme

import androidx.compose.material3.Typography

// Set of Material typography styles to start with
private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = Fonts),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = Fonts),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = Fonts),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = Fonts),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = Fonts),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = Fonts),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = Fonts),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = Fonts),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = Fonts),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = Fonts),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = Fonts),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = Fonts),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = Fonts),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = Fonts),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = Fonts)
)