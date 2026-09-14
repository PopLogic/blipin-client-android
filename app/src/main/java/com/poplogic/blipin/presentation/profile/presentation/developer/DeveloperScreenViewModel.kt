package com.poplogic.blipin.presentation.profile.presentation.developer

import com.poplogic.blipin.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class DeveloperScreenViewModel : BaseViewModel<DeveloperScreenUiState, DeveloperScreenUiEffect>() {
    override val uiState =
        MutableStateFlow<DeveloperScreenUiState>(DeveloperScreenUiState.Success(false))
    override val uiEffect = MutableSharedFlow<DeveloperScreenUiEffect>()
}
