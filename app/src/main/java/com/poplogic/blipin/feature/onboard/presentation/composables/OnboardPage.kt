package com.poplogic.blipin.feature.onboard.presentation.composables

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.poplogic.blipin.R

@Suppress("ktlint:standard:function-naming")
@Composable
fun OnboardPage(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    @RawRes lottieRawRes: Int,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(lottieRawRes),
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        Spacer(Modifier.height(20.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            minLines = 2,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(Modifier.height(4.dp))
        Text(
            subtitle,
            fontSize = 20.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center,
            color = Color(0xFF454545),
        )

        Spacer(Modifier.height(24.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(0.68f),
            contentAlignment = Alignment.Center,
        ) {
            LottieAnimation(
                composition = composition,
                iterations = 1,
                modifier =
                    Modifier
                        .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun OnboardPagePreview() {
    OnboardPage(
        title = "隨時隨地\n找到你想吃的餐車",
        subtitle = "Blipin 帶你發現附近的移動美味",
        lottieRawRes = R.raw.intro_1,
    )
}
