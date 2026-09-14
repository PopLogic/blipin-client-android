package com.poplogic.blipin.presentation.onboard.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.presentation.common.animation.rememberPagerIndicatorAnimation

@Composable
fun PagerDotIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage

            val animation =
                rememberPagerIndicatorAnimation(
                    selected = isSelected,
                    selectedWidth = 20.dp,
                    unselectedWidth = 8.dp,
                )

            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 2.dp)
                        .height(8.dp)
                        .width(animation.width)
                        .clip(RoundedCornerShape(100.dp))
                        .background(animation.color),
            )
        }
    }
}
