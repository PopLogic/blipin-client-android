package com.poplogic.blipin.feature.authenticate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.button.ButtonPrimary
import com.poplogic.blipin.common_ui.textfield.SingleLineTextField
import com.poplogic.blipin.feature.authenticate.presentation.composables.TopView
import com.poplogic.blipin.feature.authenticate.presentation.screens.PasswordBottomSheet
import com.poplogic.blipin.ui.theme.BlipinTheme
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.utils.hardcoded
import com.poplogic.blipin.utils.isEmailValid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    appNavigationController: NavController,
) {
    val textFieldState = remember { mutableStateOf("") }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.authentication_screen_bottom_animation))
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()
    val isEmailValid = textFieldState.value.isEmailValid()
    val isEmailEmpty = textFieldState.value.isEmpty()
    val isError = !isEmailEmpty && !isEmailValid
    val shouldEnableButton = !isEmailEmpty && isEmailValid
    val shouldShowPasswordBottomSheet = rememberSaveable { mutableStateOf(false) }
    val passwordBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(
                    color = Palette.SplashBackground,
                ).padding(top = WindowInsets.safeContent.asPaddingValues().calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 4.dp)
                    .background(color = Color.Transparent),
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(
                modifier = Modifier.size(48.dp),
                onClick = {
                    appNavigationController.popBackStack()
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_navigation_back_large),
                    contentDescription = "Back".hardcoded(),
                    tint = Palette.Black,
                )
            }
        }
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        focusManager.clearFocus()
                    },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopView()
            Spacer(Modifier.height(57.dp))
            SingleLineTextField(
                modifier =
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            color = Palette.Neutral.neutral50,
                            shape =
                                RoundedCornerShape(8.dp),
                        ).semantics {
                            contentType = ContentType.EmailAddress
                        },
                placeHolder = "請輸入電子信箱".hardcoded(),
                value = textFieldState.value,
                onValueChange = { textFieldState.value = it },
                trailingIcon = R.drawable.icon_clean,
                trailingIconAction = {
                    textFieldState.value = ""
                },
                isError = isError,
            )
            Spacer(Modifier.height(20.dp))
            ButtonPrimary(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    // TODO: Check if the email is registered and show the password bottom sheet
                    shouldShowPasswordBottomSheet.value = true
                },
                text = "登入/註冊".hardcoded(),
                enabled = shouldEnableButton,
            )
            Spacer(Modifier.height(16.dp))
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
            Spacer(Modifier.height(16.dp))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                IconButton(
                    modifier =
                        Modifier
                            .wrapContentHeight()
                            .aspectRatio(1f)
                            .background(color = Color.Transparent),
                    onClick = { /* Handle Google login click */ },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = "Google login".hardcoded(),
                        tint = Color.Unspecified,
                    )
                }
                Spacer(Modifier.width(16.dp))
                IconButton(
                    modifier =
                        Modifier
                            .wrapContentHeight()
                            .aspectRatio(1f)
                            .background(color = Color.Transparent),
                    onClick = { /* Handle Apple login click */ },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_apple),
                        contentDescription = "Apple login".hardcoded(),
                        tint = Color.Unspecified,
                    )
                }
            }
        }
        LottieAnimation(
            modifier =
                modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
    }

    if (shouldShowPasswordBottomSheet.value) {
        PasswordBottomSheet(
            onDismiss = { shouldShowPasswordBottomSheet.value = false },
            bottomSheetState = passwordBottomSheetState,
            email = textFieldState.value,
        )
    }
}

@Preview(device = "id:pixel_5", showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    BlipinTheme {
        AuthenticationScreen(
            modifier = Modifier.fillMaxSize(),
            appNavigationController = NavController(LocalContext.current),
        )
    }
}
