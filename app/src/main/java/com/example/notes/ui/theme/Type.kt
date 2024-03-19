package com.example.notes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    headlineMedium = TextStyle(
        fontSize = 42.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = TextStyle(
        fontSize = 25.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal
    )
)