package com.poplogic.blipin.feature.common.text

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.feature.common.R
import com.poplogic.blipin.feature.common.theme.Palette

@Composable
fun CheckableText(
    text: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.check,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
        ) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = "Unchecked",
                tint = if (isChecked) Palette.Primary.brand else Palette.Neutral.neutral200,
            )
        }
        Text(
            text = text,
            style =
                MaterialTheme.typography.labelMedium.copy(
                    color = if (isChecked) Palette.Primary.brand else Palette.Neutral.neutral200,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                ),
        )
    }
}
