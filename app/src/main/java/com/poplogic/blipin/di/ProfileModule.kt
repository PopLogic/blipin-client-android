package com.poplogic.blipin.di

import com.poplogic.blipin.feature.profile.presentation.ProfileViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val profileModule =
    module {
        viewModel<ProfileViewModel>()
    }
