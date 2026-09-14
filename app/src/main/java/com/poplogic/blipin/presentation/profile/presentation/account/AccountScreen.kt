package com.poplogic.blipin.presentation.profile.presentation.account

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.top_bar.GeneralTopAppBar
import com.poplogic.blipin.presentation.profile.presentation.account.screen.AccountSuccessScreen

@Composable
fun AccountScreen(
    onBack: () -> Unit,
    viewModel: AccountScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    AccountScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun AccountScreenContent(
    uiState: AccountScreenUiState,
    onBack: () -> Unit,
    viewModel: AccountScreenViewModel,
) {
    Scaffold(
        topBar = {
            AccountScreenTopAppBar(
                onBack = onBack,
            )
        },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is AccountScreenUiState.Loading -> {
                // Show loading indicator
            }

            is AccountScreenUiState.Error -> {
                // Show error message
            }

            is AccountScreenUiState.Success -> {
                AccountSuccessScreen(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
private fun AccountScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "帳戶".hardcoded(),
        onBack = onBack,
    )
}
