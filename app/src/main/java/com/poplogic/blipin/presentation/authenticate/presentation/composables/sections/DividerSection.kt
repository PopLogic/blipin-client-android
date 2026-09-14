package com.poplogic.blipin.presentation.authenticate.presentation.composables.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette

@Composable
internal fun DividerSection() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Palette.Neutral.neutral500,
            thickness = 1.dp,
        )
        Text(
            modifier =
                Modifier
                    .padding(horizontal = 4.dp)
                    .wrapContentWidth(),
            text = "或".hardcoded(),
            color = Palette.Neutral.neutral500,
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Palette.Neutral.neutral500,
            thickness = 1.dp,
        )
    }
}
