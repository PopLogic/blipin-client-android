package com.poplogic.blipin.di

import com.poplogic.blipin.feature.onboard.presentation.viewmodel.OnboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardModule =
    module {
        viewModelOf(::OnboardViewModel)
    }
