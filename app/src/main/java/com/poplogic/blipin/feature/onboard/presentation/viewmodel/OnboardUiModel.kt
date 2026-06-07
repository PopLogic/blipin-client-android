package com.poplogic.blipin.feature.onboard.presentation.viewmodel

import androidx.annotation.RawRes

data class OnboardUiModel(
    val title: String,
    val description: String,
    val buttonText: String,
    @RawRes val lottieRes: Int,
)
