package com.poplogic.blipin.presentation.profile.presentation.user.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography
import com.poplogic.blipin.feature.common.theme.actionButton

@Composable
fun ProfileDataRow(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    actionText: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (subTitle != null) {
            Column {
                Text(
                    text = title,
                    style = Typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = Palette.Neutral.neutralBrand,
                )
                Text(
                    text = subTitle,
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                    color = Palette.Neutral.neutral800,
                )
            }
        } else {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = Palette.Neutral.neutralBrand,
            )
        }

        actionText?.let {
            TextButton(onClick = onClick) {
                Text(
                    text = actionText,
                    style =
                        Typography.actionButton.copy(
                            fontWeight = FontWeight.Medium,
                            textDecoration = TextDecoration.Underline,
                        ),
                    color = Palette.Neutral.neutralBrand,
                )
            }
        }
    }
}
