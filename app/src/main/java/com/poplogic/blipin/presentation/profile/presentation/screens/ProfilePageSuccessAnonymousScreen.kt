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
import com.poplogic.blipin.feature.common.components.ListActionTile
import com.poplogic.blipin.feature.common.components.ListTileGap
import com.poplogic.blipin.feature.common.route.Routes
import com.poplogic.blipin.presentation.profile.presentation.composables.UserCard

@Composable
fun ProfilePageSuccessAnonymousScreen(appNavigationController: NavController) {
    UserCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(0.dp),
        onSignInClick = {
            appNavigationController.navigate(Routes.AUTHENTICATE)
        },
    )
    Spacer(modifier = Modifier.height(22.dp))
    val infos = ProfileInfoList.entries
    infos
        .filter { info -> info.allowedEnvironment.contains(Environment.fromBuildConfig()) }
        .filterNot { info -> info.needSignIn }
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
}
