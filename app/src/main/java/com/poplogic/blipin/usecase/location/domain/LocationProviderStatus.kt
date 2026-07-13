package com.poplogic.blipin.usecase.location.domain

enum class LocationProviderStatus {
    GPS_PROVIDER_DISABLED,
    NETWORK_PROVIDER_DISABLED,
    BOTH_PROVIDERS_ENABLED,
    NO_PROVIDER_ENABLED,
}
