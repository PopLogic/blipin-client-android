package com.poplogic.blipin.presentation.profile.presentation.user.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.avatar.Avatar
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography
import com.poplogic.blipin.feature.common.theme.actionButton

@Composable
fun AvatarEditSection(
    modifier: Modifier = Modifier,
    userProfileImageUrl: String?,
    onEditButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Avatar(
            modifier = Modifier.size(80.dp),
            userProfileImageUrl = userProfileImageUrl,
        )
        Row(
            modifier =
                Modifier
                    .padding(vertical = 12.dp)
                    .wrapContentWidth()
                    .clickable(onClick = onEditButtonClicked),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "變更個人頭像".hardcoded(),
                style = Typography.actionButton,
                color = Palette.Black,
            )
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.icon_navigation_next),
                contentDescription = "Edit Avatar",
                tint = Palette.Black,
            )
        }
    }
}
