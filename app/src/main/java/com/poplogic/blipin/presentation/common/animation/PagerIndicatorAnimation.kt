package com.poplogic.blipin.presentation.common.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PagerIndicatorAnimationState(
    val width: Dp,
    val color: Color,
)

@Composable
fun rememberPagerIndicatorAnimation(
    selected: Boolean,
    selectedWidth: Dp,
    unselectedWidth: Dp,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Color(0xFF888888),
): PagerIndicatorAnimationState {
    val width by animateDpAsState(
        targetValue = if (selected) selectedWidth else unselectedWidth,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "pagerIndicatorWidth",
    )
    val color by animateColorAsState(
        targetValue = if (selected) selectedColor else unselectedColor,
        animationSpec = tween(durationMillis = 300),
        label = "pagerIndicatorColor",
    )

    return PagerIndicatorAnimationState(
        width = width,
        color = color,
    )
}
