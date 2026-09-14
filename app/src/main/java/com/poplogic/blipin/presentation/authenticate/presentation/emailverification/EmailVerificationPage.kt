package com.poplogic.blipin.presentation.authenticate.presentation.emailverification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.bottom_sheet.BottomSheetTopBar
import com.poplogic.blipin.feature.common.loading.LoadingScreen
import com.poplogic.blipin.feature.common.theme.BlipinTheme
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography
import com.poplogic.blipin.presentation.authenticate.presentation.composables.widgets.OtpDigit
import com.poplogic.blipin.presentation.authenticate.presentation.emailverification.sections.EmailResendSection
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration.Companion.seconds

private const val OTP_MAX_LENGTH = 6

@Composable
fun EmailVerificationPage(
    modifier: Modifier = Modifier,
    viewModel: EmailVerificationViewModel = koinViewModel(),
    onBack: () -> Unit = {},
    onVerificationSuccess: () -> Unit = {},
    focusManager: FocusManager? = null,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    showTopBar: Boolean = true,
) {
    val uiState by viewModel.uiState.collectAsState()
    EmailVerificationContent(
        modifier = modifier,
        viewModel = viewModel,
        onBack = onBack,
        uiState = uiState,
        onVerificationSuccess = onVerificationSuccess,
        keyboardController = keyboardController,
        focusRequester = focusRequester,
        focusManager = focusManager,
        showTopBar = showTopBar,
    )
}

@Composable
fun EmailVerificationContent(
    modifier: Modifier = Modifier,
    uiState: EmailVerificationUiState,
    viewModel: EmailVerificationViewModel?,
    onBack: () -> Unit = {},
    onVerificationSuccess: () -> Unit = {},
    focusManager: FocusManager? = null,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    showTopBar: Boolean = true,
) {
    LaunchedEffect(Unit) {
        viewModel?.eventFlow?.collect { event ->
            when (event) {
                is EmailVerificationUiEffect.VerificationSuccess -> {
                    onVerificationSuccess()
                }

                is EmailVerificationUiEffect.VerificationFailed -> { // show error
                }

                is EmailVerificationUiEffect.OtpResent -> { // show resent toast
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    var isFocused by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf(TextFieldValue("")) }

    if (showTopBar) {
        BottomSheetTopBar(
            title = "註冊".hardcoded(),
            leadingIcon = R.drawable.icon_navigation_back_large,
            leadingIconAction = onBack,
            leadingIconTint = Palette.Black,
            trailingIcon = null,
            trailingIconAction = null,
        )
    }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "驗證電子郵件".hardcoded(),
            style =
                Typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Palette.Black,
                    textAlign = TextAlign.Start,
                ),
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "驗證碼已發送至 ".hardcoded(),
                style =
                    Typography.bodyMedium.copy(
                        color = Palette.Neutral.neutral600,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.W400,
                    ),
            )
            Text(
                text = uiState.email,
                style =
                    Typography.bodyMedium.copy(
                        color = Palette.Neutral.neutral600,
                        fontWeight = FontWeight.W400,
                    ),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            keyboardController?.show()
                            isFocused = true
                        }
                    },
            value = otp,
            onValueChange = { newValue ->
                if (newValue.text.length <= OTP_MAX_LENGTH) {
                    otp = newValue
                    if (newValue.text.length == OTP_MAX_LENGTH) {
                        keyboardController?.hide()
                        focusManager?.clearFocus()
                        viewModel?.onAction(EmailVerificationUiAction.OnOtpChanged(newValue.text))
                    }
                }
            },
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
            singleLine = true,
            decorationBox = { _ ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    val text = otp.text
                    repeat(OTP_MAX_LENGTH) { index ->
                        OtpDigit(
                            number = text.getOrNull(index),
                            selected =
                                (isFocused) && (
                                    (index == text.length) ||
                                        (index == OTP_MAX_LENGTH - 1 && text.length == OTP_MAX_LENGTH)
                                ),
                        )
                        if (index < OTP_MAX_LENGTH - 1) {
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                }
            },
        )

        Spacer(modifier = Modifier.height(4.dp))

        EmailResendSection(
            uiState = uiState,
            viewModel = viewModel,
        )
    }

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun EmailVerificationContentPreview() {
    BlipinTheme {
        val maxSheetHeight = (LocalConfiguration.current.screenHeightDp * 0.9f).dp
        val focusRequester: FocusRequester = remember { FocusRequester() }
        Column(
            modifier =
                Modifier
                    .fillMaxHeight(.9f)
                    .heightIn(max = maxSheetHeight)
                    .background(color = Palette.White)
                    .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize(),
            ) {
                EmailVerificationContent(
                    onBack = {},
                    onVerificationSuccess = {},
                    uiState =
                        EmailVerificationUiState(
                            email = "test@gmail.com",
                            otp = "123",
                            isLoading = false,
                            canResendOtp = false,
                            remainingTime = 30.seconds,
                        ),
                    viewModel = null,
                    keyboardController = LocalSoftwareKeyboardController.current,
                    focusRequester = focusRequester,
                )
            }
        }
    }
}
