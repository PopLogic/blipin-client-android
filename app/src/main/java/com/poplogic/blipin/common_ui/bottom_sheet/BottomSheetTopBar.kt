package com.poplogic.blipin.common_ui.bottom_sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography

private val BottomSheetTopBarActionSize = 24.dp

@Composable
fun BottomSheetTopBar(
    modifier: Modifier = Modifier,
    title: String,
    leadingIcon: Int? = null,
    leadingIconAction: (() -> Unit)? = null,
    leadingIconContentDescription: String? = null,
    leadingIconTint: Color? = null,
    trailingIcon: Int? = null,
    trailingIconAction: (() -> Unit)? = null,
    trailingIconContentDescription: String? = null,
    trailingIconTint: Color? = null,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BottomSheetTopBarIconSlot(
            icon = leadingIcon,
            action = leadingIconAction,
            contentDescription = leadingIconContentDescription,
            iconTint = leadingIconTint,
        )
        Text(
            text = title,
            style =
                Typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Palette.Black,
                ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        BottomSheetTopBarIconSlot(
            icon = trailingIcon,
            action = trailingIconAction,
            contentDescription = trailingIconContentDescription,
            iconTint = trailingIconTint,
        )
    }
}

@Composable
private fun BottomSheetTopBarIconSlot(
    icon: Int?,
    iconTint: Color? = null,
    action: (() -> Unit)?,
    contentDescription: String?,
) {
    if (icon == null) {
        Spacer(modifier = Modifier.size(BottomSheetTopBarActionSize))
        return
    }

    if (action == null) {
        Box(
            modifier = Modifier.size(BottomSheetTopBarActionSize),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                tint = iconTint ?: Color.Unspecified,
            )
        }
        return
    }

    IconButton(
        modifier = Modifier.size(BottomSheetTopBarActionSize),
        onClick = action,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = iconTint ?: Color.Unspecified,
        )
    }
}
