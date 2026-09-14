package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.composables.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.textfield.SingleLineTextField
import com.poplogic.blipin.feature.common.theme.Palette

@Composable
internal fun NicknameInputSection(
    nickname: String = "",
    onNicknameChanged: (String) -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "暱稱".hardcoded(),
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = Palette.Neutral.neutral500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium,
                ),
        )
        SingleLineTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nickname,
            onValueChange = onNicknameChanged,
            trailingIcon = null,
            placeHolder = "輸入暱稱".hardcoded(),
        )
    }
}
