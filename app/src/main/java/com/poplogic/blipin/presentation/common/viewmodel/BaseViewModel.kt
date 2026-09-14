package com.poplogic.blipin.presentation.common.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<T, R> : ViewModel() {
    abstract val uiState: MutableStateFlow<T>
    abstract val uiEffect: MutableSharedFlow<R>
}
