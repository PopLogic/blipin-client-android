package com.poplogic.blipin.common_ui.snack_bar

import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.navigation.NavController
import com.poplogic.blipin.nav.Routes
import com.poplogic.blipin.utils.hardcoded

suspend fun showLoginSuggestionSnackbar(snackbarHostState: SnackbarHostState) {
    snackbarHostState.showSnackbar(
        message = "登入享受更多功能".hardcoded(),
        actionLabel = "登入/註冊".hardcoded(),
        duration = SnackbarDuration.Indefinite,
    )
}

class LoginSuggestionSnackBarData(
    override val visuals: SnackbarVisuals,
    private val appNavigationController: NavController,
) : SnackbarData {
    override fun performAction() {
        appNavigationController.navigate(Routes.AUTHENTICATE)
    }

    override fun dismiss() {
    }
}
