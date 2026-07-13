package com.poplogic.blipin.usecase.location.domain

enum class LocationServiceStatus {
    PermissionDenied,
    CoarsePermissionGrantedAndNoProvidersEnabled,
    CoarsePermissionGrantedAndNetworkProviderDisabled,
    CoarsePermissionGrantedAndGpsProviderDisabled,
    CoarsePermissionGrantedAndBothProvidersEnabled,
    FinePermissionGrantedAndNoProvidersEnabled,
    FinePermissionGrantedAndNetworkProviderDisabled,
    FinePermissionGrantedAndGpsProviderDisabled,
    FinePermissionGrantedAndBothProvidersEnabled,
    NotDetermined,
    ;

    val canUseCoarseLocation: Boolean
        get() =
            this == CoarsePermissionGrantedAndBothProvidersEnabled || this == CoarsePermissionGrantedAndGpsProviderDisabled ||
                this == FinePermissionGrantedAndBothProvidersEnabled

    val canUseFineLocation: Boolean
        get() =
            this == FinePermissionGrantedAndBothProvidersEnabled || this == FinePermissionGrantedAndGpsProviderDisabled ||
                this == FinePermissionGrantedAndNetworkProviderDisabled

    val canUseLocation: Boolean
        get() = canUseCoarseLocation || canUseFineLocation

    companion object {
        fun fromPermissionAndProvidersStatus(
            permissionStatus: LocationPermissionStatus,
            providerStatus: LocationProviderStatus,
        ): LocationServiceStatus =
            when (permissionStatus) {
                LocationPermissionStatus.LOCATION_DENIED -> {
                    PermissionDenied
                }

                LocationPermissionStatus.COARSE_LOCATION_GRANTED -> {
                    when (providerStatus) {
                        LocationProviderStatus.NO_PROVIDER_ENABLED -> CoarsePermissionGrantedAndNoProvidersEnabled
                        LocationProviderStatus.NETWORK_PROVIDER_DISABLED -> CoarsePermissionGrantedAndNetworkProviderDisabled
                        LocationProviderStatus.GPS_PROVIDER_DISABLED -> CoarsePermissionGrantedAndGpsProviderDisabled
                        LocationProviderStatus.BOTH_PROVIDERS_ENABLED -> CoarsePermissionGrantedAndBothProvidersEnabled
                    }
                }

                LocationPermissionStatus.FINE_LOCATION_GRANTED -> {
                    when (providerStatus) {
                        LocationProviderStatus.NO_PROVIDER_ENABLED -> FinePermissionGrantedAndNoProvidersEnabled
                        LocationProviderStatus.NETWORK_PROVIDER_DISABLED -> FinePermissionGrantedAndNetworkProviderDisabled
                        LocationProviderStatus.GPS_PROVIDER_DISABLED -> FinePermissionGrantedAndGpsProviderDisabled
                        LocationProviderStatus.BOTH_PROVIDERS_ENABLED -> FinePermissionGrantedAndBothProvidersEnabled
                    }
                }

                else -> {
                    PermissionDenied
                }
            }
    }
}
