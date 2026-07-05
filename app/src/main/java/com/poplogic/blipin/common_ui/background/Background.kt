package com.poplogic.blipin.common_ui.background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.poplogic.blipin.ui.theme.hardcoded

@Composable
fun Background(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color.White),
    ) {
        Canvas(modifier.fillMaxSize()) {
            drawCircle(
                brush =
                    radialGradient(
                        colors =
                            kotlin.collections.listOf(
                                androidx.compose.ui.graphics
                                    .Color(0xFFFFDFD0)
                                    .hardcoded(),
                                androidx.compose.ui.graphics
                                    .Color(0x00FFDFD0)
                                    .hardcoded(),
                            ),
                        center =
                            Offset(
                                size.minDimension / 375f * (314.87f / 2 + 8f),
                                size.minDimension / 375f * (314.87f / 2 + 38.16f),
                            ),
                        radius = ((size.minDimension / 375f * 308.2f) / 2),
                    ),
                radius = (size.minDimension / 375f * 314.87f) / 2,
                center =
                    Offset(
                        size.minDimension / 375f * (314.87f / 2 + 8f),
                        size.minDimension / 375f * (314.87f / 2 + 38.16f),
                    ),
            )
            drawCircle(
                brush =
                    radialGradient(
                        colors =
                            kotlin.collections.listOf(
                                androidx.compose.ui.graphics
                                    .Color(0xFFD0F2FF),
                                androidx.compose.ui.graphics
                                    .Color(0x00D0F2FF),
                            ),
                        center =
                            Offset(
                                size.minDimension / 375f * (314.87f / 2 + 96f),
                                size.minDimension / 375f * (314.87f / 2 + 10.56f),
                            ),
                        radius = ((size.minDimension / 375f * 308.2f) / 2),
                    ),
                radius = (size.minDimension / 375f * 314.87f) / 2,
                center =
                    Offset(
                        size.minDimension / 375f * (314.87f / 2 + 96f),
                        size.minDimension / 375f * (314.87f / 2 + 10.56f),
                    ),
            )
            drawCircle(
                brush =
                    radialGradient(
                        colors =
                            kotlin.collections.listOf(
                                androidx.compose.ui.graphics
                                    .Color(0xFFFFD4A5),
                                androidx.compose.ui.graphics
                                    .Color(0x00FFD4A5),
                            ),
                        center =
                            Offset(
                                size.minDimension / 375f * (314.87f / 2 - 110.82f),
                                size.minDimension / 375f * (314.87f / 2 - 21.83f),
                            ),
                        radius = ((size.minDimension / 375f * 308.2f) / 2),
                    ),
                radius = (size.minDimension / 375f * 314.87f) / 2,
                center =
                    Offset(
                        size.minDimension / 375f * (314.87f / 2 - 110.82f),
                        size.minDimension / 375f * (314.87f / 2 - 21.83f),
                    ),
            )
        }
        content()
    }
}

@Preview(
    name = "Pixel 9",
    device = "id:pixel_9",
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
)
@Composable
fun BackgroundPreview() {
    Background(
        content = {
            Box {}
        },
    )
}
