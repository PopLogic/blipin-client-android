package com.poplogic.blipin.common_ui.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography
import com.poplogic.blipin.utils.hardcoded

@Composable
fun SeeAllButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(modifier.clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically) {
        Text(
            "查看全部".hardcoded(),
            style =
                Typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = Palette.Black,
                ),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier =
                Modifier
                    .width(16.dp)
                    .height(16.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.nav_icon_right_small),
                contentDescription = "See All",
                tint = Palette.Black,
            )
        }
    }
}
