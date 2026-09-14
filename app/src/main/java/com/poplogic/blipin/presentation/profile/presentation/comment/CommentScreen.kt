package com.poplogic.blipin.presentation.profile.presentation.comment

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.top_bar.GeneralTopAppBar
import com.poplogic.blipin.presentation.profile.presentation.comment.screen.CommentSuccessScreen

@Composable
fun CommentScreen(
    onBack: () -> Unit,
    viewModel: CommentScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    CommentScreenContent(
        uiState = uiState,
        onBack = onBack,
        viewModel = viewModel,
    )
}

@Composable
private fun CommentScreenContent(
    uiState: CommentScreenUiState,
    onBack: () -> Unit,
    viewModel: CommentScreenViewModel,
) {
    Scaffold(
        topBar = {
            CommentScreenTopAppBar(
                onBack = onBack,
            )
        },
        containerColor = Palette.White,
    ) { innerPadding ->
        when (uiState) {
            is CommentScreenUiState.Loading -> {
                // Show loading indicator
            }

            is CommentScreenUiState.Error -> {
                // Show error message
            }

            is CommentScreenUiState.Success -> {
                CommentSuccessScreen(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
private fun CommentScreenTopAppBar(onBack: () -> Unit) {
    GeneralTopAppBar(
        title = "評論".hardcoded(),
        onBack = onBack,
    )
}
