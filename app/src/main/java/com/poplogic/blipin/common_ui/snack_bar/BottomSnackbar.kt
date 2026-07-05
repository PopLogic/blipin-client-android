package com.poplogic.blipin.common_ui.snack_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography
import com.poplogic.blipin.ui.theme.bodyMediumRegular
import com.poplogic.blipin.utils.hardcoded

@Composable
fun BottomSnackbar(
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Row(
        modifier =
            Modifier
                .dropShadow(
                    shape = RoundedCornerShape(8.dp),
                    shadow =
                        Shadow(
                            radius = 10.dp,
                            spread = 8.dp,
                            color = Palette.Black.copy(alpha = 0.1f),
                        ),
                ).clip(RoundedCornerShape(8.dp))
                .background(Palette.White)
                .wrapContentHeight()
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            modifier =
                Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .wrapContentWidth(),
            style =
                Typography.bodyMediumRegular().copy(
                    color = Palette.Black,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (actionLabel != null) {
            TextButton(
                onClick = { onActionClick?.invoke() },
                modifier =
                    Modifier
                        .wrapContentWidth(),
            ) {
                Text(
                    text = actionLabel.hardcoded(),
                    style =
                        Typography.bodyMediumRegular().copy(
                            color = Palette.Primary.brand,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                        ),
                )
            }
        }
    }
}
