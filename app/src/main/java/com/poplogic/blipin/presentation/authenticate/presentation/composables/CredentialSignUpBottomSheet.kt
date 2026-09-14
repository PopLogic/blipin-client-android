package com.poplogic.blipin.presentation.authenticate.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.feature.common.bottom_sheet.RoundedBottomSheet
import com.poplogic.blipin.feature.common.loading.LoadingScreen
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.UpdateUserProfilePage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialSignUpBottomSheet(
    modifier: Modifier = Modifier,
    displayName: String? = null,
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    onProfileSetupSuccess: () -> Unit = {},
) {
    val isLoading = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        RoundedBottomSheet(
            bottomSheetState = bottomSheetState,
            onDismiss = onDismiss,
        ) {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val maxSheetHeight = (LocalConfiguration.current.screenHeightDp * 0.9f).dp
            val scrollState = rememberScrollState()

            Column(
                modifier =
                    Modifier
                        .fillMaxHeight(.9f)
                        .heightIn(max = maxSheetHeight)
                        .background(color = Palette.White)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
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
                UpdateUserProfilePage(
                    onProfileUpdateSuccess = onProfileSetupSuccess,
                    onLoadingStart = {
                        isLoading.value = true
                    },
                    onLoadingEnd = {
                        isLoading.value = false
                    },
                    viewModel = koinViewModel(parameters = { parametersOf(displayName) }),
                )
            }
        }
        if (isLoading.value) {
            LoadingScreen()
        }
    }
}
