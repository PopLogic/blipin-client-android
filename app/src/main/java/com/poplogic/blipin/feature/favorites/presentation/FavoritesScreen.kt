package com.poplogic.blipin.feature.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.components.PageTitle
import com.poplogic.blipin.feature.favorites.presentation.screens.FavoritesPageSuccessAnonymousScreen
import com.poplogic.blipin.utils.hardcoded
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel(),
    appNavigationController: NavHostController,
) {
    val uiState = viewModel.uiState.collectAsState()
    val horizontalPadding = dimensionResource(id = R.dimen.home_tab_horizontal_padding)
    val scrollableState = rememberScrollState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    top = horizontalPadding,
                    start = horizontalPadding,
                    end = horizontalPadding,
                ).verticalScroll(scrollableState),
        verticalArrangement = Arrangement.Top,
    ) {
        PageTitle("收藏".hardcoded())
        when (val state = uiState.value) {
            is FavoritesUiState.Loading -> {
                // Show loading indicator
            }

            is FavoritesUiState.Success -> {
                // Display the list of favorite items
                // You can create a composable function to display the list here
            }

            is FavoritesUiState.Error -> {
                // Show error message
            }

            is FavoritesUiState.SuccessAnonymous -> {
                FavoritesPageSuccessAnonymousScreen(
                    modifier = Modifier.weight(1f),
                    appNavigationController = appNavigationController,
                )
            }
        }
    }
}
