package com.poplogic.blipin.feature.authenticate.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poplogic.blipin.R
import com.poplogic.blipin.common_ui.bottom_sheet.BottomSheetTopBar
import com.poplogic.blipin.common_ui.button.ButtonPrimary
import com.poplogic.blipin.common_ui.textfield.SingleLineTextField
import com.poplogic.blipin.ui.theme.BlipinTheme
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography
import com.poplogic.blipin.utils.hardcoded
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBottomSheet(
    modifier: Modifier = Modifier,
    bottomSheetState: SheetState,
    onDismiss: () -> Unit = {},
    email: String,
) {
    val maxSheetHeight = (LocalConfiguration.current.screenHeightDp * 0.9f).dp
    val passwordTextFieldState = remember { mutableStateOf("") }
    val focusRequester: FocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(500.milliseconds)
        focusRequester.requestFocus()
    }
    ModalBottomSheet(
        sheetState = bottomSheetState,
        containerColor = Palette.White,
        dragHandle = null,
        tonalElevation = 5.dp,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        onDismissRequest = onDismiss,
        sheetGesturesEnabled = false,
        properties =
            ModalBottomSheetProperties(
                shouldDismissOnClickOutside = false,
            ),
    ) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val shouldHidePassword = rememberSaveable { mutableStateOf(true) }
        val isPasswordWrong = rememberSaveable { mutableStateOf(false) }

        Column(
            modifier =
                Modifier
                    .fillMaxHeight(.9f)
                    .heightIn(max = maxSheetHeight)
                    .background(color = Palette.White)
                    .padding(horizontal = 16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                                focusRequester.freeFocus()
                                keyboardController?.hide()
                            },
                        )
                    },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            BottomSheetTopBar(
                title = "登入".hardcoded(),
                leadingIcon = R.drawable.icon_navigation_back_large,
                trailingIcon = null,
                leadingIconAction = {
                    onDismiss()
                },
                trailingIconAction = null,
                leadingIconTint = Palette.Black,
            )
            Spacer(modifier = Modifier.height(32.dp))
            SingleLineTextField(
                value = passwordTextFieldState.value,
                onValueChange = { value ->
                    passwordTextFieldState.value = value
                },
                placeHolder = "輸入密碼".hardcoded(),
                trailingIcon =
                    when {
                        isPasswordWrong.value -> R.drawable.icon_state_error
                        shouldHidePassword.value -> R.drawable.icon_visibility_hide
                        else -> R.drawable.icon_visibility_show
                    },
                trailingIconAction = {
                    if (!(isPasswordWrong.value)) {
                        shouldHidePassword.value = !shouldHidePassword.value
                    }
                },
                isPassword = shouldHidePassword.value,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .semantics {
                            contentType = ContentType.Password
                        },
                isError = isPasswordWrong.value,
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                keyboardOptions =
                    KeyboardOptions(
                        capitalization = KeyboardCapitalization.Unspecified,
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            focusRequester.freeFocus()
                            keyboardController?.hide()
                        },
                    ),
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "忘記密碼".hardcoded(),
                    style =
                        Typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.Center,
                        ),
                    color = Palette.Neutral.neutralBrand,
                    modifier =
                        Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .clickable(onClick = {
                                // Handle forgot password click
                            }),
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            ButtonPrimary(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                text = "登入".hardcoded(),
                onClick = {
                    // Handle login click
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PasswordBottomSheetPreview() {
    val bottomSheetState =
        rememberModalBottomSheetState()
    BlipinTheme {
        PasswordBottomSheet(
            bottomSheetState = bottomSheetState,
            email = "SamChen@poplogic.com",
            modifier = Modifier.fillMaxSize(),
        )
    }
}
