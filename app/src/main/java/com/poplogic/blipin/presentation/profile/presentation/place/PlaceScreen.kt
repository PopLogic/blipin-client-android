package com.poplogic.blipin.presentation.profile.presentation.place

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.top_bar.GeneralTopAppBar
import com.poplogic.blipin.presentation.profile.presentation.place.screen.PlaceSuccessScreen

@Composable
fun PlaceScreen(
    onBack: () -> Unit,
    viewModel: PlaceScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    PlaceScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun PlaceScreenContent(
    uiState: PlaceScreenUiState,
    onBack: () -> Unit,
    viewModel: PlaceScreenViewModel,
) {
    Scaffold(
        topBar = {
            PlaceScreenTopAppBar(
                onBack = onBack,
            )
        },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is PlaceScreenUiState.Loading -> {
                // Show loading indicator
            }

            is PlaceScreenUiState.Error -> {
                // Show error message
            }

            is PlaceScreenUiState.Success -> {
                PlaceSuccessScreen(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
private fun PlaceScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "地點".hardcoded(),
        onBack = onBack,
    )
}
