package com.poplogic.blipin.di

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.poplogic.blipin.usecase.connectivity.ConnectivityUseCase
import com.poplogic.blipin.usecase.connectivity.ConnectivityUseCaseImpl
import org.koin.dsl.module

val networkModule =
    module {
        single<ConnectivityManager> {
            get<Context>().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        factory<ConnectivityUseCase> {
            ConnectivityUseCaseImpl(get())
        }
    }
