package com.poplogic.blipin.presentation.profile.presentation.comment

sealed interface CommentScreenUiState {
    data object Loading : CommentScreenUiState

    data object Error : CommentScreenUiState

    data class Success(
        val isLoading: Boolean,
    ) : CommentScreenUiState
}
