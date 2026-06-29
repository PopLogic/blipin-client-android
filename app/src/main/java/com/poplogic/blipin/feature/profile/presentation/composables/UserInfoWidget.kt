package com.poplogic.blipin.feature.profile.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.ui.theme.BlipinBlack
import com.poplogic.blipin.ui.theme.BlipinNeutral600
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun UserInfoWidget(
    infoTitle: String,
    infoValue: String,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = infoTitle, style = Typography.labelMedium.copy(color = BlipinNeutral600))
        Text(text = infoValue, style = Typography.bodyMedium.copy(color = BlipinBlack))
    }
}
