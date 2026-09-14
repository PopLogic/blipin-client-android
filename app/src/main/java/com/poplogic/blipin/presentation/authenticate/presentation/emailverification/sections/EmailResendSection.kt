package com.poplogic.blipin.presentation.authenticate.presentation.emailverification.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.feature.common.theme.Palette
import com.poplogic.blipin.feature.common.theme.Typography
import com.poplogic.blipin.presentation.authenticate.presentation.emailverification.EmailVerificationUiAction
import com.poplogic.blipin.presentation.authenticate.presentation.emailverification.EmailVerificationUiState
import com.poplogic.blipin.presentation.authenticate.presentation.emailverification.EmailVerificationViewModel

@Composable
fun EmailResendSection(
    viewModel: EmailVerificationViewModel? = null,
    uiState: EmailVerificationUiState,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (uiState.canResendOtp) {
            TextButton(
                onClick = {
                    viewModel?.onAction(EmailVerificationUiAction.OnResendOtpButtonClicked)
                },
            ) {
                Text(
                    text = "再次寄送認證碼".hardcoded(),
                    style =
                        Typography.bodyMedium.copy(
                            color = Palette.Primary.brand,
                            fontWeight = FontWeight.Medium,
                        ),
                    textDecoration = TextDecoration.Underline,
                )
            }
        } else {
            TextButton(
                onClick = {},
                enabled = false,
            ) {
                Text(
                    text =
                        "再次寄送驗證碼 (${
                            uiState.remainingTime.toComponents { minutes, seconds, nanoseconds ->
                                String.format(
                                    locale = LocalLocale.current.platformLocale,
                                    "%1dm%2ds",
                                    minutes,
                                    seconds,
                                )
                            }
                        })".hardcoded(),
                    style =
                        Typography.bodyMedium.copy(
                            color = Palette.Primary.primary200,
                            fontWeight = FontWeight.Medium,
                        ),
                )
            }
        }
    }
}
