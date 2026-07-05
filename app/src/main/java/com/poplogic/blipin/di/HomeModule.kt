package com.poplogic.blipin.di

import com.poplogic.blipin.feature.explore.presentation.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule =
    module {
        includes(
            networkModule,
            commonUiModule,
        )

        viewModelOf(::ExploreViewModel)
    }
