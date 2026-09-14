package com.poplogic.blipin.presentation.authenticate.presentation.composables

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.bottom_sheet.BottomSheetTopBar
import com.poplogic.blipin.feature.common.bottom_sheet.RoundedBottomSheet
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.presentation.authenticate.presentation.composables.widgets.SignUpPageIndicator
import com.poplogic.blipin.presentation.authenticate.presentation.emailverification.EmailVerificationPage
import com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.UpdateUserProfilePage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private enum class SignUpStep { EMAIL_VERIFICATION, PROFILE }

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmailSignUpBottomSheet(
    modifier: Modifier = Modifier,
    email: String,
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    onSignUpSuccess: () -> Unit = {},
) {
    RoundedBottomSheet(
        bottomSheetState = bottomSheetState,
        onDismiss = onDismiss,
    ) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester: FocusRequester = remember { FocusRequester() }
        val maxSheetHeight = (LocalConfiguration.current.screenHeightDp * 0.9f).dp
        val pagerState =
            rememberPagerState(
                initialPage = SignUpStep.EMAIL_VERIFICATION.ordinal,
                pageCount = { SignUpStep.entries.size },
            )
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier =
                Modifier
                    .fillMaxHeight(.9f)
                    .heightIn(max = maxSheetHeight)
                    .background(color = Palette.White)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            },
                        )
                    },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BackHandler {
                if (pagerState.currentPage == SignUpStep.EMAIL_VERIFICATION.ordinal) {
                    onDismiss()
                }
                // No action for the PROFILE page, as the back button is disabled there
            }

            BottomSheetTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "註冊".hardcoded(),
                leadingIcon =
                    if (pagerState.currentPage ==
                        SignUpStep.EMAIL_VERIFICATION.ordinal
                    ) {
                        R.drawable.icon_navigation_back_large
                    } else {
                        null
                    },
                leadingIconAction = {
                    if (pagerState.currentPage == SignUpStep.EMAIL_VERIFICATION.ordinal) {
                        onDismiss()
                    }
                },
                leadingIconTint = Palette.Black,
            )

            SignUpPageIndicator(
                modifier = Modifier.padding(horizontal = 16.dp),
                pageCount = SignUpStep.entries.size,
                currentPage = pagerState.currentPage,
            )

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) { page ->
                when (SignUpStep.entries[page]) {
                    SignUpStep.EMAIL_VERIFICATION -> {
                        EmailVerificationPage(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 28.dp, start = 16.dp, end = 16.dp),
                            onBack = onDismiss,
                            onVerificationSuccess = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(SignUpStep.PROFILE.ordinal)
                                }
                            },
                            keyboardController = keyboardController,
                            focusRequester = focusRequester,
                            focusManager = focusManager,
                            viewModel = koinViewModel(parameters = { parametersOf(email) }),
                            showTopBar = false,
                        )
                    }

                    SignUpStep.PROFILE -> {
                        UpdateUserProfilePage(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 28.dp, start = 16.dp, end = 16.dp),
                            onBack = null,
                            onProfileUpdateSuccess = onSignUpSuccess,
                            showTopBar = false,
                        )
                    }
                }
            }
        }
    }
}
