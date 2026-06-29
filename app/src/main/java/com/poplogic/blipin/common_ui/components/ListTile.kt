package com.poplogic.blipin.common_ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.ui.theme.BlipinBlack
import com.poplogic.blipin.ui.theme.BlipinNeutral100
import com.poplogic.blipin.ui.theme.BlipinNeutral800
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun ListActionTile(
    modifier: Modifier = Modifier,
    title: String,
    onTap: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onTap),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = Typography.bodyLarge.copy(color = BlipinBlack),
        )
        Icon(
            painterResource(id = R.drawable.nav_icon_right_small),
            contentDescription = null,
            tint = BlipinNeutral800,
        )
    }
}

@Composable
fun ListTile(
    title: String,
    description: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = Typography.bodyLarge.copy(color = BlipinBlack),
        )
        Text(
            text = description,
            style = Typography.labelMedium.copy(color = BlipinNeutral800),
        )
    }
}

@Composable
fun ListTileGap() {
    Box(
        modifier =
            Modifier
                .height(8.dp)
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = BlipinNeutral100,
        )
    }
}
