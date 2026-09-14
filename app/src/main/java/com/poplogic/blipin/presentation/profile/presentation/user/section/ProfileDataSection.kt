package com.poplogic.blipin.presentation.profile.presentation.user.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.presentation.profile.presentation.user.ProfileDataItem
import com.poplogic.blipin.presentation.profile.presentation.user.ProfileEditViewModel
import com.poplogic.blipin.presentation.profile.presentation.user.composable.ProfileDataRow

@Composable
fun ProfileDataSection(
    modifier: Modifier,
    viewModel: ProfileEditViewModel,
    userEntity: UserEntity,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .dropShadow(
                    shape = RoundedCornerShape(10.dp),
                    shadow =
                        Shadow(
                            color = Palette.ShadowColor,
                            radius = 10.dp,
                            offset = DpOffset(x = 0.dp, y = 0.dp),
                            spread = 0.dp,
                        ),
                ).clip(shape = RoundedCornerShape(10.dp))
                .background(color = Palette.White),
    ) {
        ProfileDataItem.entries.forEachIndexed { index, item ->
            when (item) {
                ProfileDataItem.DisplayName -> {
                    ProfileDataRow(
                        title = item.title,
                        subTitle = userEntity.displayName,
                        onClick = { viewModel.onAction(item.uiAction) },
                        actionText = "編輯".hardcoded(),
                    )
                }

                ProfileDataItem.Birthdate -> {
                    ProfileDataRow(
                        title = item.title,
                        subTitle = userEntity.birthdate?.toString(),
                        onClick = { viewModel.onAction(item.uiAction) },
                        actionText = "編輯".hardcoded(),
                    )
                }

                ProfileDataItem.Gender -> {
                    ProfileDataRow(
                        title = item.title,
                        subTitle = userEntity.gender?.uiName,
                        onClick = { viewModel.onAction(item.uiAction) },
                        actionText = "編輯".hardcoded(),
                    )
                }
            }
            if (index != ProfileDataItem.entries.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Palette.Neutral.neutral50,
                    thickness = 1.dp,
                )
            }
        }
    }
}
