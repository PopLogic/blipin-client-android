package com.poplogic.blipin.presentation.profile.presentation.account

import com.poplogic.blipin.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class AccountScreenViewModel : BaseViewModel<AccountScreenUiState, AccountScreenUiEffect>() {
    override val uiState =
        MutableStateFlow<AccountScreenUiState>(AccountScreenUiState.Success(false))
    override val uiEffect = MutableSharedFlow<AccountScreenUiEffect>()
}
