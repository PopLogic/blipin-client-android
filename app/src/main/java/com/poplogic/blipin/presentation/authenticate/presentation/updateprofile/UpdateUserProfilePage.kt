package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.bottom_sheet.BottomSheetTopBar
import com.poplogic.blipin.feature.common.button.ButtonPrimary
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography
import com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.composables.sections.BirthdaySettingSection
import com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.composables.sections.GenderSettingSection
import com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.composables.sections.NicknameInputSection
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UpdateUserProfilePage(
    modifier: Modifier = Modifier,
    viewModel: UpdateUserProfileViewModel = koinViewModel(parameters = { parametersOf("") }),
    onBack: (() -> Unit)? = null,
    onLoadingStart: () -> Unit = {},
    onLoadingEnd: () -> Unit = {},
    onProfileUpdateSuccess: () -> Unit = {},
    showTopBar: Boolean = true,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (onBack != null) {
        BackHandler {
            onBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UpdateUserProfileUiEffect.ProfileUpdateSuccess -> {
                    onProfileUpdateSuccess()
                }

                is UpdateUserProfileUiEffect.ProfileUpdateFailed -> { // show error
                }
            }
        }
    }
    if (uiState.isLoading) {
        onLoadingStart()
    } else {
        onLoadingEnd()
    }

    if (showTopBar) {
        BottomSheetTopBar(
            title = "註冊".hardcoded(),
            leadingIcon = if (onBack != null) R.drawable.icon_navigation_back_large else null,
            leadingIconAction = onBack,
            leadingIconTint = Palette.Black,
            trailingIcon = null,
            trailingIconAction = null,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "您的帳號已成功建立".hardcoded(),
            style =
                Typography.titleLarge.copy(
                    color = Palette.Black,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                ),
        )
        Text(
            text = "請設定個人資料也可以稍後再填".hardcoded(),
            style =
                Typography.bodyLarge.copy(
                    color = Palette.Neutral.neutral500,
                    fontWeight = FontWeight.W400,
                ),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "設定個人資料".hardcoded(),
            style =
                Typography.bodyLarge.copy(
                    color = Palette.Black,
                    fontWeight = FontWeight.W700,
                ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier =
                Modifier
                    .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            NicknameInputSection(
                nickname = uiState.nickname,
                onNicknameChanged = { nickname ->
                    viewModel.onAction(UpdateUserProfileUiAction.OnNicknameChanged(nickname))
                },
            )
            Spacer(modifier = Modifier.height(20.dp))
            BirthdaySettingSection(
                birthday = uiState.birthday,
                onBirthdayChanged = { birthday ->
                    viewModel.onAction(UpdateUserProfileUiAction.OnBirthdayChanged(birthday))
                },
            )

            Spacer(modifier = Modifier.height(20.dp))
            GenderSettingSection(
                selectedGender = uiState.gender,
                onGenderChanged = { gender ->
                    viewModel.onAction(UpdateUserProfileUiAction.OnGenderChanged(gender))
                },
            )
        }

        ButtonPrimary(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(min = 48.dp),
            text = "完成".hardcoded(),
            enabled = uiState.canSubmit && !uiState.isLoading,
            onClick = {
                viewModel.onAction(UpdateUserProfileUiAction.OnSubmitButtonClicked)
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "稍後再說".hardcoded(),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(onClick = {
                        onProfileUpdateSuccess()
                    }),
            style =
                Typography.bodyMedium.copy(
                    color = Palette.Neutral.neutralBrand,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                ),
        )
        Spacer(modifier = Modifier.height(13.dp))
    }
}

@Preview
@Composable
fun UpdateUserProfilePagePreview() {
    UpdateUserProfilePage(
        onBack = {},
        onProfileUpdateSuccess = {},
    )
}
