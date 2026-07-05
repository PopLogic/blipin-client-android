package com.poplogic.blipin.di

import androidx.compose.material3.SnackbarHostState
import org.koin.dsl.module

val commonUiModule =
    module {
        single<SnackbarHostState> {
            SnackbarHostState()
        }
    }
