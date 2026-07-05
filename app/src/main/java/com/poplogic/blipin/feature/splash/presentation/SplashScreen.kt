package com.poplogic.blipin.feature.splash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.poplogic.blipin.R
import com.poplogic.blipin.ui.theme.splashBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToOnboard: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true, // Control animation playback
        restartOnPlay = false,
    )
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(progress) {
        if (progress >= 1f && !hasNavigated) {
            hasNavigated = true
            delay(500)
            onNavigateToOnboard()
        }
    }
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = splashBackground),
        verticalArrangement = Arrangement.Center,
    ) {
        LottieAnimation(
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            composition = composition,
            progress = { progress },
        )
    }
}
