package com.poplogic.blipin.feature.common.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.R
import com.poplogic.blipin.feature.common.theme.Palette

@Composable
fun AnonymousAvatar(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .clip(CircleShape)
                .background(color = Palette.Neutral.neutral50),
    ) {
        Icon(
            painter = painterResource(R.drawable.avatar_default),
            contentDescription = "Default Avatar".hardcoded(),
            modifier =
                Modifier
                    .size(32.dp)
                    .align(Alignment.Center),
            tint = Palette.Neutral.neutral200,
        )
    }
}
