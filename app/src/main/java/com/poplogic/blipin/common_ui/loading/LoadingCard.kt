package com.poplogic.blipin.common_ui.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.poplogic.blipin.R

@Composable
fun LoadingCard(
    modifier: Modifier = Modifier,
    width: Dp? = null,
    height: Dp? = null,
    borderRadius: Dp = 8.dp,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.skeleton_loading),
    )
    Box(
        modifier =
            modifier
                .then(
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
                ).clip(RoundedCornerShape(borderRadius)),
    ) {
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.Crop,
        )
    }
}
