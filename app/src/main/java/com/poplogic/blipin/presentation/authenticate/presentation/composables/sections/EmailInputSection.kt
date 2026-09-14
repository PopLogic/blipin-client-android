package com.poplogic.blipin.presentation.authenticate.presentation.composables.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.button.ButtonPrimary
import com.poplogic.blipin.feature.common.textfield.SingleLineTextField
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.presentation.authenticate.presentation.AuthenticationUiAction
import com.poplogic.blipin.presentation.authenticate.presentation.AuthenticationUiState
import com.poplogic.blipin.presentation.authenticate.presentation.AuthenticationViewModel

@Composable
internal fun EmailInputSection(
    modifier: Modifier = Modifier,
    email: String,
    onValueChange: (String) -> Unit,
    onClearButtonTapped: () -> Unit,
    isError: Boolean,
    isButtonEnabled: Boolean,
    onEmailSignInButtonClicked: () -> Unit,
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    viewModel: AuthenticationViewModel,
    uiState: AuthenticationUiState,
) {
    SingleLineTextField(
        modifier =
            modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    color = Palette.Neutral.neutral50,
                    shape =
                        RoundedCornerShape(8.dp),
                ).semantics {
                    contentType = ContentType.EmailAddress
                },
        keyboardController = keyboardController,
        focusRequester = focusRequester,
        placeHolder = "請輸入電子信箱".hardcoded(),
        value = email,
        onValueChange = onValueChange,
        trailingIcon = R.drawable.icon_clean,
        trailingIconAction = onClearButtonTapped,
        isError = isError,
        keyboardActions =
            KeyboardActions(
                onDone = {
                    focusRequester.freeFocus()
                    keyboardController?.hide()
                    if (isButtonEnabled) {
                        viewModel.onAction(AuthenticationUiAction.OnEmailSignInButtonClicked(uiState.email))
                    }
                },
            ),
    )

    Spacer(Modifier.height(20.dp))
    ButtonPrimary(
        modifier = Modifier.fillMaxWidth(),
        onClick = onEmailSignInButtonClicked,
        text = "登入/註冊".hardcoded(),
        enabled = isButtonEnabled,
    )
}
