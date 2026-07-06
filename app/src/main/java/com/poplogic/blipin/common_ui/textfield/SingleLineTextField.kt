package com.poplogic.blipin.common_ui.textfield

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun SingleLineTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: Int?,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeHolder,
                style =
                    Typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Palette.Neutral.neutral300,
                    ),
            )
        },
        singleLine = true,
        trailingIcon =
            if (trailingIcon == null || value.isEmpty()) {
                null
            } else {
                @Composable {
                    Icon(
                        painter =
                            androidx.compose.ui.res
                                .painterResource(id = trailingIcon),
                        contentDescription = null,
                    )
                }
            },
    )
}
