package com.poplogic.blipin

import androidx.compose.material3.SnackbarHostState
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.poplogic.blipin")
class AppModule {
    @Single
    fun snackbarHostState(): SnackbarHostState = SnackbarHostState()
}
