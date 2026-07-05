package com.poplogic.blipin.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography =
    Typography(
        headlineLarge =
            TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 24.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
            ),
        headlineSmall =
            TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                lineHeight = 30.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                lineHeight = 26.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Center,
            ),
        titleLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
    )

fun Typography.bodyMediumBold() =
    this.bodyMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
    )

fun Typography.bodyMediumRegular() =
    this.bodyMedium.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    )
