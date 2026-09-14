package com.poplogic.blipin.presentation.profile.presentation.comment

import com.poplogic.blipin.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class CommentScreenViewModel : BaseViewModel<CommentScreenUiState, CommentScreenUiEffect>() {
    override val uiState =
        MutableStateFlow<CommentScreenUiState>(CommentScreenUiState.Success(false))
    override val uiEffect = MutableSharedFlow<CommentScreenUiEffect>()
}
