package com.poplogic.blipin.presentation.profile.presentation.developer

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.top_bar.GeneralTopAppBar
import com.poplogic.blipin.presentation.profile.presentation.developer.screen.DeveloperSuccessScreen

@Composable
fun DeveloperScreen(
    onBack: () -> Unit,
    viewModel: DeveloperScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    DeveloperScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun DeveloperScreenContent(
    uiState: DeveloperScreenUiState,
    onBack: () -> Unit,
    viewModel: DeveloperScreenViewModel,
) {
    Scaffold(
        topBar = {
            DeveloperScreenTopAppBar(
                onBack = onBack,
            )
        },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is DeveloperScreenUiState.Loading -> {
                // Show loading indicator
            }

            is DeveloperScreenUiState.Error -> {
                // Show error message
            }

            is DeveloperScreenUiState.Success -> {
                DeveloperSuccessScreen(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
private fun DeveloperScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "開發人員".hardcoded(),
        onBack = onBack,
    )
}
