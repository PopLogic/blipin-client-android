package com.poplogic.blipin.feature.profile.presentation.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.button.ButtonSecondary
import com.poplogic.blipin.common_ui.components.ListActionTile
import com.poplogic.blipin.common_ui.components.ListTileGap
import com.poplogic.blipin.common_ui.components.PageTitle
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
    val horizontalPadding = dimensionResource(id = R.dimen.home_tab_horizontal_padding)
    val scrollableState = rememberScrollableState { delta -> delta }
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    start = horizontalPadding,
                    end = horizontalPadding,
                    top = horizontalPadding,
                ).scrollable(state = scrollableState, orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.Top,
    ) {
        PageTitle("個人檔案")
        Spacer(modifier = Modifier.height(28.dp))
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
}
