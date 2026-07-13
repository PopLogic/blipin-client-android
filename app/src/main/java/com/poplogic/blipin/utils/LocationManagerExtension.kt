package com.poplogic.blipin.utils

import android.location.LocationManager
import com.poplogic.blipin.usecase.location.domain.LocationProviderStatus

fun LocationManager.locationRequiredProvidersStatus(): LocationProviderStatus {
    val isGpsProviderEnabled = isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkProviderEnabled = isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    return when {
        isGpsProviderEnabled && isNetworkProviderEnabled -> LocationProviderStatus.BOTH_PROVIDERS_ENABLED
        isGpsProviderEnabled -> LocationProviderStatus.NETWORK_PROVIDER_DISABLED
        isNetworkProviderEnabled -> LocationProviderStatus.GPS_PROVIDER_DISABLED
        else -> LocationProviderStatus.NO_PROVIDER_ENABLED
    }
}
