package com.poplogic.blipin.presentation.profile.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.poplogic.blipin.common.base.environment.Environment
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.button.ButtonSecondary
import com.poplogic.blipin.feature.common.components.ListActionTile
import com.poplogic.blipin.feature.common.components.ListTileGap
import com.poplogic.blipin.nav.navigateToMyComments
import com.poplogic.blipin.nav.navigateToMyPlaces
import com.poplogic.blipin.nav.navigateToProfileEdit
import com.poplogic.blipin.presentation.profile.presentation.ProfileUiAction
import com.poplogic.blipin.presentation.profile.presentation.ProfileUiState
import com.poplogic.blipin.presentation.profile.presentation.ProfileViewModel
import com.poplogic.blipin.presentation.profile.presentation.composables.UserCard

@Composable
fun ProfilePageSuccessScreen(
    uiState: ProfileUiState.Success,
    viewModel: ProfileViewModel,
    appNavigationController: NavController,
) {
    UserCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(0.dp),
        userEntity = uiState.userEntity,
        onEditProfileClick = appNavigationController::navigateToProfileEdit,
        onMyPlacesClick = appNavigationController::navigateToMyPlaces,
        onMyCommentClick = appNavigationController::navigateToMyComments,
    )
    Spacer(modifier = Modifier.height(22.dp))
    val infos = ProfileInfoList.entries
    infos
        .filter { info -> info.allowedEnvironment.contains(Environment.fromBuildConfig()) }
        .forEachIndexed { index, info ->
            ListActionTile(
                modifier = Modifier.height(48.dp),
                title = info.label,
                onTap = {
                    info.route?.let { route ->
                        appNavigationController.navigate(route)
                    }
                },
            )
            if (index != infos.lastIndex) {
                ListTileGap()
            }
        }
    Spacer(modifier = Modifier.height(32.dp))
    ButtonSecondary(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(48.dp),
        text = "登出".hardcoded(),
        onClick = {
            viewModel.onAction(ProfileUiAction.OnLogOutButtonClick)
        },
    )
}
