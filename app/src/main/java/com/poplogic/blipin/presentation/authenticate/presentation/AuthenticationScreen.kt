package com.poplogic.blipin.presentation.authenticate.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.loading.LoadingScreen
import com.poplogic.blipin.feature.common.theme.BlipinTheme
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.presentation.authenticate.presentation.composables.CredentialSignUpBottomSheet
import com.poplogic.blipin.presentation.authenticate.presentation.composables.EmailSignInBottomSheet
import com.poplogic.blipin.presentation.authenticate.presentation.composables.EmailSignUpBottomSheet
import com.poplogic.blipin.presentation.authenticate.presentation.composables.sections.CredentialButtonsSection
import com.poplogic.blipin.presentation.authenticate.presentation.composables.sections.DividerSection
import com.poplogic.blipin.presentation.authenticate.presentation.composables.sections.EmailInputSection
import com.poplogic.blipin.presentation.authenticate.presentation.composables.sections.TopViewSection
import com.poplogic.blipin.presentation.common.clearFocusOnTapOutside
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    appNavigationController: NavController,
    credentialManager: CredentialManager = koinInject(),
    viewModel: AuthenticationViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.authentication_screen_bottom_animation))
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()
    val shouldShowEmailSignInBottomSheet = rememberSaveable { mutableStateOf(false) }
    val shouldShowEmailSignUpBottomSheet = rememberSaveable { mutableStateOf(false) }
    val shouldShowCredentialSignUpBottomSheet = rememberSaveable { mutableStateOf(false) }
    val emailSignInBottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { targetValue -> targetValue != SheetValue.Hidden },
        )
    val emailSignUpBottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { targetValue -> targetValue != SheetValue.Hidden },
        )
    val credentialSignUpBottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { targetValue -> targetValue != SheetValue.Hidden },
        )
    val context = LocalContext.current
    val emailInputFieldFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        emailInputFieldFocusRequester.requestFocus()
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AuthenticationUiEffect.ShowCredentialSignUpBottomSheet -> {
                    shouldShowCredentialSignUpBottomSheet.value = true
                }

                is AuthenticationUiEffect.ShowEmailSignInBottomSheet -> {
                    shouldShowEmailSignInBottomSheet.value = true
                }

                is AuthenticationUiEffect.ShowEmailSignUpBottomSheet -> {
                    shouldShowEmailSignUpBottomSheet.value = true
                }

                is AuthenticationUiEffect.NavigateToHomeScreen -> {
                    appNavigationController.navigate("home") {
                        popUpTo("authentication") { inclusive = true }
                    }
                }

                is AuthenticationUiEffect.ShowEmailVerificationSheet -> {
                    // Handle email verification sheet if needed
                }

                is AuthenticationUiEffect.ShowEmailCheckError -> {
                    // Handle error - show snackbar/toast
                }

                null -> {
                    Log.d("AuthenticationScreen", "Received null event")
                }
            }
        }
    }

    Box {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(
                        color = Palette.SplashBackground,
                    ).padding(
                        top = WindowInsets.safeContent.asPaddingValues().calculateTopPadding(),
                    ).clearFocusOnTapOutside(focusManager),
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
                        .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopViewSection()
                Spacer(Modifier.height(57.dp))
                EmailInputSection(
                    modifier = Modifier.fillMaxWidth(),
                    email = uiState.email,
                    onValueChange = { newEmail ->
                        viewModel.onAction(
                            AuthenticationUiAction.OnEmailTextFieldChanged(newEmail),
                        )
                    },
                    onClearButtonTapped = {
                        viewModel.onAction(AuthenticationUiAction.OnEmailTextFieldCleared)
                    },
                    focusRequester = emailInputFieldFocusRequester,
                    keyboardController = keyboardController,
                    isError = uiState.isEmailStateError,
                    isButtonEnabled = uiState.isEmailValid,
                    onEmailSignInButtonClicked = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        viewModel.onAction(AuthenticationUiAction.OnEmailSignInButtonClicked(uiState.email))
                    },
                    viewModel = viewModel,
                    uiState = uiState,
                )
                Spacer(Modifier.height(16.dp))
                DividerSection()
                Spacer(Modifier.height(16.dp))
                CredentialButtonsSection(
                    viewModel = viewModel,
                    credentialManager = credentialManager,
                    context = context,
                )
            }
            LottieAnimation(
                modifier =
                    modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                composition = lottieComposition,
                iterations = LottieConstants.IterateForever,
            )
        }
        if (uiState.isLoading) {
            LoadingScreen()
        }
    }

    if (shouldShowEmailSignUpBottomSheet.value) {
        EmailSignUpBottomSheet(
            onDismiss = { shouldShowEmailSignUpBottomSheet.value = false },
            bottomSheetState = emailSignUpBottomSheetState,
            email = uiState.email,
            onSignUpSuccess = {
                shouldShowEmailSignUpBottomSheet.value = false
                appNavigationController.navigate("home") {
                    popUpTo("authentication") { inclusive = true }
                }
            },
        )
    }
    if (shouldShowCredentialSignUpBottomSheet.value) {
        CredentialSignUpBottomSheet(
            displayName = uiState.credentialDisplayName,
            onDismiss = { shouldShowCredentialSignUpBottomSheet.value = false },
            bottomSheetState = credentialSignUpBottomSheetState,
            onProfileSetupSuccess = {
                shouldShowCredentialSignUpBottomSheet.value = false
                viewModel.onAction(AuthenticationUiAction.OnCredentialFailed)
                appNavigationController.navigate("home") {
                    popUpTo("authentication") { inclusive = true }
                }
            },
        )
    }
    if (shouldShowEmailSignInBottomSheet.value) {
        EmailSignInBottomSheet(
            onDismiss = { shouldShowEmailSignInBottomSheet.value = false },
            bottomSheetState = emailSignInBottomSheetState,
            email = uiState.email,
            onSignInSuccess = {
                shouldShowEmailSignInBottomSheet.value = false
                appNavigationController.navigate("home") {
                    popUpTo("authentication") { inclusive = true }
                }
            },
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
