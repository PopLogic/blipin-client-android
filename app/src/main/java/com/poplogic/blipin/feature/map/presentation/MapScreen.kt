package com.poplogic.blipin.feature.map.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.textfield.SingleLineTextField
import com.poplogic.blipin.feature.explore.presentation.sections.FilterChipsSection
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.usecase.location.domain.LocationPermissionStatus
import com.poplogic.blipin.usecase.location.domain.LocationServiceStatus
import com.poplogic.blipin.utils.hardcoded
import org.koin.androidx.compose.koinViewModel

@Composable
@SuppressLint("UnrememberedMutableState")
fun MapScreen(viewModel: MapViewModel = koinViewModel<MapViewModel>()) {
    val context = LocalContext.current
    val locationState = viewModel.locationState.collectAsState()
    val locationServiceStatus = viewModel.locationServiceStatus.collectAsState()

    val uTown = locationState.value?.toLatLng() ?: LatLng(0.0, 0.0)
    val cameraPositionState =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(uTown, 15f)
        }

    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.skeleton_loading),
    )
    val hasRequestedPermission = rememberSaveable { mutableStateOf(false) }

    val searchTextFieldState = rememberSaveable { mutableStateOf("") }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            viewModel.onUiAction(
                MapUiAction.LocationPermissionRequestResponded(
                    LocationPermissionStatus.fromPermissionsMap(permissions),
                ),
            )
        }

    LaunchedEffect(locationServiceStatus.value) {
        when {
            locationServiceStatus.value == LocationServiceStatus.NotDetermined || locationServiceStatus.value.canUseFineLocation -> {
            }

            else -> {
                when {
                    context is Activity &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                        )
                    -> {
                        // In an educational UI, explain to the user why your app requires this
                        // permission for a specific feature to behave as expected, and what
                        // features are disabled if it's declined. In this UI, include a
                        // "cancel" or "no thanks" button that lets the user continue
                        // using your app without granting the permission.
                        // Show an educational UI to explain the permission request
                        Log.d("ExploreScreen", "Should show rationale for location permission")
                    }

                    else -> {
                        launcher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                            ),
                        )
                        hasRequestedPermission.value = true
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (locationState.value) {
            null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    LottieAnimation(
                        modifier = Modifier.fillMaxSize(),
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            else -> {
                val firstLoaded = rememberSaveable { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    if (firstLoaded.value) {
                        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(uTown, 15f))
                        firstLoaded.value = false
                    }
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState =
                    cameraPositionState,
                ) {
                    Marker(
                        state = MarkerState(position = uTown),
                        title = "UTown",
                        snippet = "Marker in UTown",
                        icon =
                            run {
                                val original =
                                    ContextCompat
                                        .getDrawable(
                                            context,
                                            R.drawable.brunch_activated,
                                        )!!
                                        .toBitmap()
                                val shadowRadius = 12f
                                val shadowDx = 2f
                                val shadowDy = 4f
                                val padded =
                                    createBitmap(
                                        original.width + (shadowRadius * 2).toInt(),
                                        original.height + (shadowRadius * 2).toInt() + shadowDy.toInt(),
                                    )
                                val canvas = android.graphics.Canvas(padded)
                                val paint =
                                    android.graphics
                                        .Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)
                                        .apply {
                                            maskFilter =
                                                android.graphics.BlurMaskFilter(
                                                    shadowRadius,
                                                    android.graphics.BlurMaskFilter.Blur.NORMAL,
                                                )
                                            colorFilter =
                                                android.graphics.PorterDuffColorFilter(
                                                    android.graphics.Color.argb(80, 0, 0, 0),
                                                    android.graphics.PorterDuff.Mode.SRC_IN,
                                                )
                                        }
                                canvas.drawBitmap(
                                    original,
                                    shadowRadius + shadowDx,
                                    shadowRadius + shadowDy,
                                    paint,
                                )
                                canvas.drawBitmap(original, shadowRadius, shadowRadius, null)
                                BitmapDescriptorFactory.fromBitmap(padded)
                            },
                    )
                }
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(uTown, 15f)
                        },
                        modifier =
                            Modifier
                                .height(48.dp)
                                .aspectRatio(1f)
                                .padding(all = 6.dp)
                                .dropShadow(
                                    shape = CircleShape,
                                    shadow =
                                        Shadow(
                                            radius = 4.5.dp,
                                            spread = 0.dp,
                                            color = Color.Black.copy(alpha = 0.15f),
                                            offset = DpOffset(1.dp, 4.dp),
                                        ),
                                ).clip(CircleShape)
                                .background(color = Palette.White),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_location),
                            contentDescription = "定位".hardcoded(),
                            tint = Palette.Primary.brand,
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))

                    SingleLineTextField(
                        value = searchTextFieldState.value,
                        onValueChange = {
                            searchTextFieldState.value = it
                        },
                        placeHolder = "搜尋".hardcoded(),
                        modifier =
                            Modifier
                                .weight(1f)
                                .wrapContentHeight(),
                        leadingIcon = R.drawable.icon_search,
                        leadingIconTint = Palette.Primary.brand,
                        leadingIconAction = null,
                        trailingIcon = R.drawable.icon_filter_adjustment,
                        trailingIconTint = Palette.Primary.brand,
                        trailingIconAction = null,
                        isError = false,
                        isPassword = false,
                        cornerRadius = 100.dp,
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(
                        onClick = {
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(uTown, 15f)
                        },
                        modifier =
                            Modifier
                                .height(48.dp)
                                .aspectRatio(1f)
                                .padding(all = 6.dp)
                                .dropShadow(
                                    shape = CircleShape,
                                    shadow =
                                        Shadow(
                                            radius = 4.5.dp,
                                            spread = 0.dp,
                                            color = Color.Black.copy(alpha = 0.15f),
                                            offset = DpOffset(1.dp, 4.dp),
                                        ),
                                ).clip(CircleShape)
                                .background(color = Palette.White),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_overflow),
                            contentDescription = "定位".hardcoded(),
                            tint = Palette.Primary.brand,
                        )
                    }
                }

                FilterChipsSection(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    backgroundColor = Color.Transparent,
                )
            }
        }
    }
}
