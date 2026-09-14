package com.poplogic.blipin.presentation.profile.presentation.support

import com.poplogic.blipin.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class SupportScreenViewModel : BaseViewModel<SupportScreenUiState, SupportScreenUiEffect>() {
    override val uiState =
        MutableStateFlow<SupportScreenUiState>(SupportScreenUiState.Success(false))
    override val uiEffect = MutableSharedFlow<SupportScreenUiEffect>()
}
