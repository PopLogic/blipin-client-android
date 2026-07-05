package com.poplogic.blipin.common_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.poplogic.blipin.ui.theme.BlipinBlack
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun PageTitle(title: String) {
    Box(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    ) {
        Text(
            text = title,
            style =
                Typography.headlineLarge.copy(
                    color = BlipinBlack,
                    fontWeight = FontWeight.Bold,
                ),
            maxLines = 1,
        )
    }
}
