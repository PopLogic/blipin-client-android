package com.poplogic.blipin.presentation.profile.presentation.notification

import com.poplogic.blipin.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class NotificationScreenViewModel : BaseViewModel<NotificationScreenUiState, NotificationScreenUiEffect>() {
    override val uiState =
        MutableStateFlow<NotificationScreenUiState>(NotificationScreenUiState.Success(false))
    override val uiEffect = MutableSharedFlow<NotificationScreenUiEffect>()
}
