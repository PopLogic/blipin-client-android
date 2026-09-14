package com.poplogic.blipin.presentation.common.dialog

import androidx.compose.runtime.MutableState

// The Manager accepts a type parameter 'T' restricted to your custom dialog hierarchies
interface DialogManager<T : Any> {
    // Expose as State to prevent direct external mutation while remaining observable
    val activeDialog: MutableState<T?>

    fun showDialog(dialog: T)

    fun dismissDialog()
}
