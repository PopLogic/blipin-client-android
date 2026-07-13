package com.poplogic.blipin.usecase.location.domain

import com.google.android.gms.maps.model.LatLng

data class Location(
    val latitude: Double,
    val longitude: Double,
) {
    fun toLatLng(): LatLng = LatLng(latitude, longitude)
}
