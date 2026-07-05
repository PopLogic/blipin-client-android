package com.poplogic.blipin.common_ui.loading

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.ui.theme.Palette

@Composable
fun LoadingCardWithDescription(
    width: Dp? = null,
    height: Dp? = null,
    borderRadius: Dp = 8.dp,
) {
    Card(
        modifier =
            Modifier.then(
                when {
                    width != null && height != null -> {
                        Modifier.size(
                            width,
                            height,
                        )
                    }

                    width != null && height == null -> {
                        Modifier
                            .width(width)
                            .fillMaxHeight()
                    }

                    width == null && height != null -> {
                        Modifier
                            .height(height)
                            .fillMaxWidth()
                    }

                    else -> {
                        Modifier.fillMaxSize()
                    }
                },
            ),
        shape = RoundedCornerShape(borderRadius),
        colors = CardDefaults.cardColors(containerColor = Palette.White),
        border =
            BorderStroke(
                width = 1.dp,
                color = Palette.Neutral.neutral200,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            LoadingCard(
                modifier = Modifier.weight(160 / 250f),
                borderRadius = 0.dp,
            )
            LoadingDescription(modifier = Modifier.weight(90 / 250f))
        }
    }
}

@Composable
private fun LoadingDescription(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(26f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LoadingCard(
                modifier =
                    Modifier
                        .fillMaxWidth(80 / 256f)
                        .fillMaxHeight(),
                borderRadius = 100.dp,
            )

            LoadingCard(
                modifier =
                    Modifier
                        .fillMaxWidth(24 / 256f)
                        .fillMaxHeight()
                        .clip(CircleShape),
                borderRadius = 100.dp,
            )
        }
        Spacer(modifier = Modifier.weight(8f))
        LoadingCard(
            modifier =
                Modifier
                    .fillMaxWidth(120 / 256f)
                    .weight(16f),
            borderRadius = 100.dp,
        )
        Spacer(modifier = Modifier.weight(11f))
        LoadingCard(
            modifier =
                Modifier
                    .fillMaxWidth(160 / 256f)
                    .weight(16f),
            borderRadius = 100.dp,
        )
    }
}
