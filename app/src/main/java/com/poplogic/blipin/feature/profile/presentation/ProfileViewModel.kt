package com.poplogic.blipin.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel : ViewModel() {
    private val _uiState =
        flowOf(
            ProfileUiState.SuccessAnonymous,
//            ProfileUiState.Success(
//                userName = "Sarah",
//                userEmail = "Sarah@email.com",
//                userProfilePictureUrl = "https://poplogic.github.io/assets/ic_launcher.png",
//                birthdayString = "2000-01-01",
//            ),
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUiState.Loading,
        )
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
    }
}
