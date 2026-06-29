package com.poplogic.blipin.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel : ViewModel() {
    private val _uiState =
        flowOf(
            ProfileUiState.Success(
                userName = "Sarah",
                userEmail = "Sarah@email.com",
                userProfilePictureUrl = "https://poplogic.github.io/assets/ic_launcher.png",
                birthdayString = "2000-01-01",
            ),
        ).map {
            delay(500)
            it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUiState.Loading,
        )
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
    }
}
