package com.poplogic.blipin.feature.common.loading

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.poplogic.blipin.feature.common.R
import com.poplogic.blipin.feature.common.theme.Palette

@Composable
fun LoadingScreen(backgroundColor: Color = Palette.Neutral.neutral900.copy(alpha = 0.5f)) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .clickable(enabled = true, onClick = { /* obscure clicks */ }),
        contentAlignment = Alignment.Center,
    ) {
        BackHandler {
            // Disable back button while loading
        }
        LottieAnimation(
            modifier = Modifier.size(100.dp),
            composition =
                rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
                    .value,
            iterations = LottieConstants.IterateForever,
        )
    }
}
