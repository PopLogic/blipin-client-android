package com.poplogic.blipin.presentation.profile.presentation.support

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.top_bar.GeneralTopAppBar
import com.poplogic.blipin.presentation.profile.presentation.support.screen.SupportSuccessScreen

@Composable
fun SupportScreen(
    onBack: () -> Unit,
    viewModel: SupportScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    SupportScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun SupportScreenContent(
    uiState: SupportScreenUiState,
    onBack: () -> Unit,
    viewModel: SupportScreenViewModel,
) {
    Scaffold(
        topBar = {
            SupportScreenTopAppBar(
                onBack = onBack,
            )
        },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is SupportScreenUiState.Loading -> {
                // Show loading indicator
            }

            is SupportScreenUiState.Error -> {
                // Show error message
            }

            is SupportScreenUiState.Success -> {
                SupportSuccessScreen(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
private fun SupportScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "更多資訊與支援".hardcoded(),
        onBack = onBack,
    )
}
