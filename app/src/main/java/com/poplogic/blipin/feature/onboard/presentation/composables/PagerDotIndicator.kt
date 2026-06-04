package com.poplogic.blipin.feature.onboard.presentation.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerDotIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage

            // 1. Animate the width smoothly
            val width by animateDpAsState(
                targetValue = if (isSelected) 20.dp else 8.dp,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "width",
            )

            // 2. Animate the color smoothly
            val color by animateColorAsState(
                targetValue =
                    if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color(
                            0xFF888888,
                        )
                    },
                animationSpec = tween(durationMillis = 300),
                label = "color",
            )

            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 2.dp) // Replaces your Spacer logic for cleaner code
                        .height(8.dp)
                        .width(width)
                        .clip(RoundedCornerShape(100.dp))
                        .background(color),
            )
        }
    }
}
