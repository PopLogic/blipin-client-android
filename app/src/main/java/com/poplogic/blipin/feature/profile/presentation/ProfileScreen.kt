package com.poplogic.blipin.feature.profile.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.components.PageTitle
import com.poplogic.blipin.feature.profile.presentation.screens.ProfilePageSuccessAnonymousScreen
import com.poplogic.blipin.feature.profile.presentation.screens.ProfilePageSuccessScreen
import com.poplogic.blipin.utils.hardcoded
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    appNavigationController: NavController,
    scrollState: ScrollState = rememberScrollState(),
) {
    val uiState = viewModel.uiState.collectAsState()
    val horizontalPadding = dimensionResource(id = R.dimen.home_tab_horizontal_padding)

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    top = horizontalPadding,
                    start = horizontalPadding,
                    end = horizontalPadding,
                ).verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
    ) {
        PageTitle("個人檔案".hardcoded())
        Spacer(modifier = Modifier.height(28.dp))
        when (val state = uiState.value) {
            is ProfileUiState.Loading -> {
                // Show loading indicator
            }

            is ProfileUiState.Success -> {
                ProfilePageSuccessScreen(
                    modifier = modifier,
                    userName = state.userName,
                    userEmail = state.userEmail,
                    userProfilePictureUrl = state.userProfilePictureUrl,
                    birthdayString = state.birthdayString,
                )
            }

            is ProfileUiState.SuccessAnonymous -> {
                ProfilePageSuccessAnonymousScreen(appNavigationController)
            }

            is ProfileUiState.Error -> {
                // Show error message
            }
        }
    }
}
