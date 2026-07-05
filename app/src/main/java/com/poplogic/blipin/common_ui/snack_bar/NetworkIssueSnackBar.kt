package com.poplogic.blipin.common_ui.snack_bar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.poplogic.blipin.utils.hardcoded

suspend fun showNetworkIssueSnackBar(snackbarHostState: SnackbarHostState) {
    snackbarHostState.showSnackbar(
        message = "無法連線至網路或Wi-Fi".hardcoded(),
        duration = SnackbarDuration.Indefinite,
    )
}
