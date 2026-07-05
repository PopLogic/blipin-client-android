package com.poplogic.blipin.feature.profile.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common_ui.button.ButtonSecondary
import com.poplogic.blipin.common_ui.components.ListActionTile
import com.poplogic.blipin.common_ui.components.ListTileGap
import com.poplogic.blipin.feature.profile.presentation.composables.UserCard
import com.poplogic.blipin.utils.hardcoded

@Composable
fun ProfilePageSuccessScreen(
    modifier: Modifier = Modifier,
    userName: String,
    userEmail: String,
    userProfilePictureUrl: String,
    birthdayString: String,
) {
    UserCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(0.dp),
        userName = userName,
        userEmail = userEmail,
        userProfilePictureUrl = userProfilePictureUrl,
        birthdayString = birthdayString,
    )
    Spacer(modifier = Modifier.height(22.dp))
    val infos = ProfileInfoList.entries
    infos.forEachIndexed { index, info ->
        ListActionTile(
            modifier = Modifier.height(48.dp),
            title = info.label,
            onTap = { /* Handle tap action */ },
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
        onClick = { /* Handle logout action */ },
    )
}
