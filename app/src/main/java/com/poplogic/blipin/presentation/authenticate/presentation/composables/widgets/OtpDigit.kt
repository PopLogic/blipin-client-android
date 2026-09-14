package com.poplogic.blipin.presentation.authenticate.presentation.composables.widgets

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.feature.common.theme.BlipinTheme
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography

@Composable
fun OtpDigit(
    number: Char?,
    selected: Boolean,
) {
    Box(
        modifier =
            Modifier
                .size(48.dp)
                .border(
                    1.dp,
                    if (selected) Palette.Primary.brand else Palette.Neutral.neutral300,
                    RoundedCornerShape(12.dp),
                ).background(Palette.White, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = (number ?: ' ').toString(),
            style =
                Typography.bodyMedium.copy(
                    color = Palette.Neutral.neutral950,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium,
                ),
            modifier = Modifier.align(Alignment.Center),
        )

        if (selected && number == null) {
            val infiniteTransition = rememberInfiniteTransition(label = "Cursor Animation")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec =
                    infiniteRepeatable(
                        animation = tween(500),
                        repeatMode = RepeatMode.Reverse,
                    ),
                label = "Cursor Alpha",
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.align(Alignment.Center),
            ) {
                Box(
                    Modifier
                        .width(1.dp)
                        .height(20.dp)
                        .background(Palette.Neutral.neutral950.copy(alpha = alpha)),
                )
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun OtpDigitPreview() {
    BlipinTheme {
        OtpDigit(number = '5', selected = true)
    }
}
