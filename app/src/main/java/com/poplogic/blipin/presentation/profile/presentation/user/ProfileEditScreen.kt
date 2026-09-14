package com.poplogic.blipin.presentation.profile.presentation.user

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.top_bar.GeneralTopAppBar

@Composable
fun ProfileEditScreen(
    onBack: () -> Unit,
    viewModel: ProfileEditViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    ProfileEditScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun ProfileEditScreenContent(
    uiState: ProfileEditScreenUiState,
    onBack: () -> Unit,
    viewModel: ProfileEditViewModel,
) {
    Scaffold(
        topBar = {
            UserScreenTopAppBar(
                onBack = onBack,
            )
        },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is ProfileEditScreenUiState.Loading -> {
                // Show loading indicator
            }

            is ProfileEditScreenUiState.Error -> {
                // Show error message
            }

            is ProfileEditScreenUiState.Success -> {
                ProfileEditSuccessScreen(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "個人資料".hardcoded(),
        onBack = onBack,
    )
}
