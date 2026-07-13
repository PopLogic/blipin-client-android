package com.poplogic.blipin.common_ui.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.R
import com.poplogic.blipin.ui.theme.BlipinTheme
import com.poplogic.blipin.ui.theme.Palette
import com.poplogic.blipin.ui.theme.Typography

@Composable
fun SingleLineTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: Int?,
    trailingIconAction: (() -> Unit)? = null,
    trailingIconTint: Color? = null,
    leadingIcon: Int? = null,
    leadingIconAction: (() -> Unit)? = null,
    leadingIconTint: Color? = null,
    isError: Boolean = false,
    isPassword: Boolean = false,
    cornerRadius: Dp = 8.dp,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier =
            modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        keyboardController?.show()
                    }
                },
        value = value,
        isError = isError,
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Palette.Neutral.neutral300,
                unfocusedBorderColor = Palette.Neutral.neutral300,
                errorBorderColor = Palette.Alert,
                focusedContainerColor = Palette.White,
                unfocusedContainerColor = Palette.White,
                errorContainerColor = Palette.White,
                focusedTextColor = Palette.Neutral.neutral900,
                unfocusedTextColor = Palette.Neutral.neutral900,
                errorTextColor = Palette.Neutral.neutral900,
                errorTrailingIconColor = Palette.Alert,
            ),
        keyboardOptions =
            keyboardOptions,
        keyboardActions =
            keyboardActions,
        shape = RoundedCornerShape(cornerRadius),
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeHolder,
                style =
                    Typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Palette.Neutral.neutral300,
                    ),
            )
        },
        singleLine = true,
        interactionSource = interactionSource,
        trailingIcon =
            if (trailingIcon == null || value.isEmpty()) {
                null
            } else {
                {
                    IconButton(
                        onClick = {
                            trailingIconAction?.invoke()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = trailingIcon),
                            contentDescription = null,
                            tint = trailingIconTint ?: Color.Unspecified,
                        )
                    }
                }
            },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon =
            if (leadingIcon == null) {
                null
            } else {
                {
                    IconButton(
                        onClick = {
                            leadingIconAction?.invoke()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = leadingIcon),
                            contentDescription = null,
                            tint = leadingIconTint ?: Color.Unspecified,
                        )
                    }
                }
            },
    )
}

@Preview
@Composable
fun SingleLineTextFieldPreview() {
    BlipinTheme {
        SingleLineTextField(
            placeHolder = "Enter text",
            value = "",
            onValueChange = {},
            trailingIcon = R.drawable.icon_clean,
        )
    }
}

@Preview
@Composable
fun SingleLineTextFieldErrorStatePreview() {
    BlipinTheme {
        SingleLineTextField(
            placeHolder = "Enter text",
            value = "",
            onValueChange = {},
            trailingIcon = R.drawable.icon_clean,
            isError = true,
        )
    }
}
