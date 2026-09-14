package com.poplogic.blipin.presentation.profile.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.components.PageTitle
import com.poplogic.blipin.feature.common.loading.LoadingScreen
import com.poplogic.blipin.presentation.profile.presentation.screens.ProfilePageSuccessAnonymousScreen
import com.poplogic.blipin.presentation.profile.presentation.screens.ProfilePageSuccessScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    appNavigationController: NavController,
    scrollState: ScrollState = rememberScrollState(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState(initial = null)
    val horizontalPadding = dimensionResource(id = R.dimen.home_tab_horizontal_padding)

    LaunchedEffect(uiEvent) {
        uiEvent?.let { event ->
            when (event) {
                is ProfileUiEvent.ShowGeneralErrorDialog -> {
                    appNavigationController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            }
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    top = horizontalPadding,
                    start = horizontalPadding,
                    end = horizontalPadding,
                ),
        verticalArrangement = Arrangement.Top,
    ) {
        PageTitle("個人檔案".hardcoded())
        Spacer(modifier = Modifier.height(28.dp))
        when (val currentState = uiState) {
            ProfileUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingScreen(
                        backgroundColor = Color.Transparent,
                    )
                }
            }

            is ProfileUiState.Success -> {
                ProfilePageSuccessScreen(
                    uiState = currentState,
                    viewModel = viewModel,
                    appNavigationController = appNavigationController,
                )
                if (currentState.isLoading) {
                    LoadingScreen()
                }
            }

            is ProfileUiState.SuccessAnonymous -> {
                ProfilePageSuccessAnonymousScreen(appNavigationController)
                if (currentState.isLoading) {
                    LoadingScreen()
                }
            }

            is ProfileUiState.Error -> {
                // Show error message
            }
        }
    }
}
