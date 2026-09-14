package com.poplogic.blipin.presentation.profile.presentation.place

import com.poplogic.blipin.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class PlaceScreenViewModel : BaseViewModel<PlaceScreenUiState, PlaceScreenUiEffect>() {
    override val uiState =
        MutableStateFlow<PlaceScreenUiState>(PlaceScreenUiState.Success(false))
    override val uiEffect = MutableSharedFlow<PlaceScreenUiEffect>()
}
