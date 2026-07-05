package com.poplogic.blipin.common_ui.snack_bar

import androidx.compose.material3.SnackbarDuration
import com.poplogic.blipin.utils.hardcoded

enum class SnackbarType(
    val message: String,
    val actionLabel: String? = null,
    val duration: SnackbarDuration = SnackbarDuration.Indefinite,
    val isDismissible: Boolean = true,
) {
    LOGIN_SUGGESTION(
        message = "請先登入以使用此功能".hardcoded(),
        actionLabel = "登入".hardcoded(),
        duration = SnackbarDuration.Indefinite,
        isDismissible = true,
    ),
    NETWORK_ISSUE(
        message = "無法連線至網路或Wi-Fi".hardcoded(),
        actionLabel = "重試".hardcoded(),
        duration = SnackbarDuration.Indefinite,
        isDismissible = false,
    ),
    ;

    companion object {
        fun showSnackBar(
            snackbar: SnackbarType,
            onActionClick: (() -> Unit)? = null,
        ) {
            // Implement the logic to show the snackbar using the provided parameters
            // This is a placeholder for the actual implementation
        }
    }
}
