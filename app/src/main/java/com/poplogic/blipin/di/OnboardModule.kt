package com.poplogic.blipin.di

import com.poplogic.blipin.feature.onboard.presentation.viewmodel.OnboardViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val onboardModule =
    module {
        viewModel<OnboardViewModel>()
    }
