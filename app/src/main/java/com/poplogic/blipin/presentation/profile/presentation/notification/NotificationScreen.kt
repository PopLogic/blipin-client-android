package com.poplogic.blipin.presentation.profile.presentation.notification

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.top_bar.GeneralTopAppBar
import com.poplogic.blipin.presentation.profile.presentation.notification.screen.NotificationSuccessScreen

@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    viewModel: NotificationScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    NotificationScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun NotificationScreenContent(
    uiState: NotificationScreenUiState,
    onBack: () -> Unit,
    viewModel: NotificationScreenViewModel,
) {
    Scaffold(
        topBar = {
            NotificationScreenTopAppBar(
                onBack = onBack,
            )
        },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is NotificationScreenUiState.Loading -> {
                // Show loading indicator
            }

            is NotificationScreenUiState.Error -> {
                // Show error message
            }

            is NotificationScreenUiState.Success -> {
                NotificationSuccessScreen(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
private fun NotificationScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "通知".hardcoded(),
        onBack = onBack,
    )
}
