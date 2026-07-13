package com.poplogic.blipin.common_ui.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.unit.dp
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
    val animatedContainerColor by animateColorAsState(
        targetValue = if (enabled) BlipinBrandPrimary else BlipinNeutral100,
        animationSpec = tween(durationMillis = 220),
        label = "primary_container_color",
    )
    val animatedContentColor by animateColorAsState(
        targetValue = BlipinWhite,
        animationSpec = tween(durationMillis = 220),
        label = "primary_content_color",
    )

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = animatedContainerColor,
                contentColor = animatedContentColor,
                disabledContentColor = animatedContentColor,
                disabledContainerColor = animatedContainerColor,
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
    val animatedContainerColor by animateColorAsState(
        targetValue = if (enabled) BlipinWhite else BlipinNeutral100,
        animationSpec = tween(durationMillis = 220),
        label = "secondary_container_color",
    )
    val animatedContentColor by animateColorAsState(
        targetValue = if (enabled) BlipinBrandPrimary else BlipinNeutral100,
        animationSpec = tween(durationMillis = 220),
        label = "secondary_content_color",
    )
    val animatedBorderColor by animateColorAsState(
        targetValue = if (enabled) BlipinBrandPrimary else Color.Transparent,
        animationSpec = tween(durationMillis = 220),
        label = "secondary_border_color",
    )

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = animatedContainerColor,
                contentColor = animatedContentColor,
                disabledContentColor = animatedContentColor,
                disabledContainerColor = animatedContainerColor,
            ),
        border = BorderStroke(width = 1.dp, brush = Brush.linearGradient(colors = listOf(animatedBorderColor, animatedBorderColor))),
    ) {
        Text(text, style = Typography.labelLarge)
    }
}
