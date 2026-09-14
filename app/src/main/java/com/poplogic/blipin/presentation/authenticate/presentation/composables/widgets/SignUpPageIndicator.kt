package com.poplogic.blipin.presentation.authenticate.presentation.composables.widgets

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.feature.common.theme.Palette

@Composable
internal fun SignUpPageIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(Color.Transparent),
    ) {
        repeat(pageCount) { index ->
            val color =
                if (index == currentPage) {
                    Palette.Primary.brand
                } else {
                    Palette.Neutral.neutral100
                }
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(color)
                        .clip(
                            RoundedCornerShape(0.5f),
                        ),
            )
            if (index < pageCount - 1) {
                Box(modifier = Modifier.width(4.dp))
            }
        }
    }
}
