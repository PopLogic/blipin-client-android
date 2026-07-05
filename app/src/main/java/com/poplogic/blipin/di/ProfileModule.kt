package com.poplogic.blipin.di

import com.poplogic.blipin.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileModule =
    module {
        viewModelOf(::ProfileViewModel)
    }
