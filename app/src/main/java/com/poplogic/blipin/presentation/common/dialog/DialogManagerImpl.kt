package com.poplogic.blipin.presentation.common.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// The Manager accepts a type parameter 'T' restricted to your custom dialog hierarchies
class DialogManagerImpl<T : Any> : DialogManager<T> {
    // Expose as State to prevent direct external mutation while remaining observable
    private val _activeDialog = mutableStateOf<T?>(null)
    override val activeDialog: MutableState<T?> = _activeDialog

    override fun showDialog(dialog: T) {
        _activeDialog.value = dialog
    }

    override fun dismissDialog() {
        _activeDialog.value = null
    }
}

// Inline helper to quickly instantiate the manager in Composables
@Composable
fun <T : Any> rememberDialogManager(): DialogManager<T> = remember { DialogManagerImpl() }
