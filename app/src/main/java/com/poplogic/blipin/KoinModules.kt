package com.poplogic.blipin

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.material3.SnackbarHostState
import androidx.credentials.CredentialManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.poplogic.blipin.feature.explore.presentation.ExploreViewModel
import com.poplogic.blipin.feature.favorites.presentation.FavoritesViewModel
import com.poplogic.blipin.feature.map.presentation.MapViewModel
import com.poplogic.blipin.feature.onboard.presentation.viewmodel.OnboardViewModel
import com.poplogic.blipin.feature.profile.presentation.ProfileViewModel
import com.poplogic.blipin.usecase.authentication.CheckIfEmailRegisteredFlowBasedUseCase
import com.poplogic.blipin.usecase.authentication.CheckIfEmailRegisteredFlowBasedUseCaseImpl
import com.poplogic.blipin.usecase.connectivity.ConnectivityFlowBasedUseCase
import com.poplogic.blipin.usecase.connectivity.ConnectivityFlowBasedUseCaseImpl
import com.poplogic.blipin.usecase.location.location_service_status.CanLocationServiceBeenUsedUseCase
import com.poplogic.blipin.usecase.location.location_service_status.CanLocationServiceBeenUsedUseCaseImpl
import com.poplogic.blipin.usecase.location.location_updates.GetFineLocationUpdatesFlowBasedUseCaseImpl
import com.poplogic.blipin.usecase.location.location_updates.GetLocationUpdatesFlowBasedUseCase
import com.poplogic.blipin.usecase.location.location_updates.GetLocationUpdatesFlowBasedUseCaseProvider
import com.poplogic.blipin.usecase.location.location_updates.GetLocationUpdatesFlowBasedUseCaseProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val coreModule =
    module {
        single { SnackbarHostState() }
        single<ConnectivityManager> {
            androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
        single<FusedLocationProviderClient> {
            LocationServices.getFusedLocationProviderClient(androidContext())
        }
        single<CredentialManager> {
            CredentialManager.create(androidContext())
        }
    }

private val useCaseModule =
    module {
        factory<CheckIfEmailRegisteredFlowBasedUseCase> { CheckIfEmailRegisteredFlowBasedUseCaseImpl() }
        factory<ConnectivityFlowBasedUseCase> { ConnectivityFlowBasedUseCaseImpl(get()) }
        factory<CanLocationServiceBeenUsedUseCase> {
            CanLocationServiceBeenUsedUseCaseImpl(
                get(),
            )
        }
        factory<GetLocationUpdatesFlowBasedUseCase> {
            GetFineLocationUpdatesFlowBasedUseCaseImpl(
                get(),
                get(),
            )
        }
        factory<GetLocationUpdatesFlowBasedUseCaseProvider> {
            GetLocationUpdatesFlowBasedUseCaseProviderImpl(
                get(),
                get(),
            )
        }
    }

private val viewModelModule =
    module {
        viewModel { FavoritesViewModel() }
        viewModel { ProfileViewModel() }
        viewModel { OnboardViewModel() }
        viewModel { MapViewModel(get(), get()) }
        viewModel { ExploreViewModel(get(), get()) }
    }

val appModules = listOf(coreModule, useCaseModule, viewModelModule)
