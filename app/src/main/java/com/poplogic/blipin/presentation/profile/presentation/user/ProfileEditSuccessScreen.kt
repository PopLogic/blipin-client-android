package com.poplogic.blipin.presentation.profile.presentation.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.presentation.profile.presentation.user.section.AvatarEditSection
import com.poplogic.blipin.presentation.profile.presentation.user.section.ProfileDataSection

@Composable
fun ProfileEditSuccessScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileEditScreenUiState.Success,
    innerPadding: PaddingValues,
    viewModel: ProfileEditViewModel,
) {
    val userProfilePictureUrl = uiState.userEntity.profilePictureUrl
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp),
        ) {
            AvatarEditSection(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                userProfileImageUrl = userProfilePictureUrl,
                onEditButtonClicked = {},
            )
            Spacer(modifier = Modifier.height(20.dp))
            ProfileDataSection(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                userEntity = uiState.userEntity,
                viewModel = viewModel,
            )
        }
    }
}
