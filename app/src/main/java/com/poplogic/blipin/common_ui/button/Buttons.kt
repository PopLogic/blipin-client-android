package com.poplogic.blipin.common_ui.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.poplogic.blipin.ui.theme.BlipinBrandPrimary
import com.poplogic.blipin.ui.theme.BlipinNeutral100
import com.poplogic.blipin.ui.theme.BlipinWhite
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = BlipinBrandPrimary,
                contentColor = BlipinWhite,
                disabledContentColor = BlipinNeutral100,
                disabledContainerColor = BlipinWhite,
            ),
    ) {
        Text(text, style = Typography.labelLarge)
    }
}

@Composable
fun ButtonSecondary(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = BlipinWhite,
                contentColor = BlipinBrandPrimary,
                disabledContentColor = BlipinNeutral100,
                disabledContainerColor = BlipinNeutral100,
            ),
        border =
            ButtonDefaults.outlinedButtonBorder(enabled).copy(
                brush =
                    Brush.linearGradient(
                        colors = listOf(BlipinBrandPrimary, BlipinBrandPrimary),
                    ),
            ),
    ) {
        Text(text, style = Typography.labelLarge)
    }
}
