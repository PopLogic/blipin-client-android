package com.poplogic.blipin.common_ui.snack_bar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.poplogic.blipin.utils.hardcoded

suspend fun showLoginSuggestionSnackbar(snackbarHostState: SnackbarHostState) {
    snackbarHostState.showSnackbar(
        message = "登入享受更多功能".hardcoded(),
        actionLabel = "登入/註冊".hardcoded(),
        duration = SnackbarDuration.Indefinite,
    )
}
