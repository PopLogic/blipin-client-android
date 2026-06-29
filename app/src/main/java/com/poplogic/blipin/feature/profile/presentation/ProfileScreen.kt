package com.poplogic.blipin.feature.profile.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.poplogic.blipin.feature.profile.presentation.screens.ProfilePageSuccessScreen

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
) {
    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is ProfileUiState.Loading -> {
            // Show loading indicator
        }

        is ProfileUiState.Success -> {
            Log.d(
                "ProfileScreen",
                "Profile data loaded successfully: ${state.userName}, ${state.userEmail}, ${state.userProfilePictureUrl}, ${state.birthdayString}",
            )
            ProfilePageSuccessScreen(
                modifier = modifier,
                userName = state.userName,
                userEmail = state.userEmail,
                userProfilePictureUrl = state.userProfilePictureUrl,
                birthdayString = state.birthdayString,
            )
        }

        is ProfileUiState.Error -> {
            // Show error message
        }
    }
}
