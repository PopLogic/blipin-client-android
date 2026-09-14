package com.poplogic.blipin.feature.common.avatar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.poplogic.blipin.common.base.util.hardcoded

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    userProfileImageUrl: String?,
) {
    if (userProfileImageUrl.isNullOrEmpty()) {
        AnonymousAvatar(
            modifier =
                modifier
                    .clip(CircleShape),
        )
    } else {
        AsyncImage(
            model = userProfileImageUrl,
            contentDescription = "User Profile Picture".hardcoded(),
            modifier =
                modifier
                    .clip(CircleShape),
            clipToBounds = true,
            contentScale = ContentScale.Fit,
        )
    }
}
