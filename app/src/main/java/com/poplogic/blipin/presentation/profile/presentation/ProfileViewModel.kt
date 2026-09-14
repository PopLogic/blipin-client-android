package com.poplogic.blipin.presentation.profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.domain.auth.model.UserAuthenticationState
import com.poplogic.blipin.domain.auth.usecase.LogOutUseCase
import com.poplogic.blipin.domain.auth.usecase.UserAuthenticationStateUseCase
import com.poplogic.blipin.domain.user.usecase.GetUserProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val userAuthenticationStateUseCase: UserAuthenticationStateUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<ProfileUiEvent>(extraBufferCapacity = 1)
    val uiEvent: MutableSharedFlow<ProfileUiEvent> = _uiEvent

    init {
        viewModelScope.launch {
            userAuthenticationStateUseCase(Unit).collect { userAuthenticationState ->
                when (userAuthenticationState) {
                    UserAuthenticationState.Undetermined -> {
                        _uiState.emit(ProfileUiState.Loading)
                    }

                    UserAuthenticationState.Unauthenticated -> {
                        _uiState.emit(ProfileUiState.SuccessAnonymous(isLoading = false))
                    }

                    UserAuthenticationState.Authenticated -> {
                        val userProfileUseCaseResult = getUserProfileUseCase(Unit)
                        userProfileUseCaseResult.getOrNull()?.let { userEntity ->
                            _uiState.emit(
                                ProfileUiState.Success(
                                    userEntity = userEntity,
                                    isLoading = false,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAction(action: ProfileUiAction) {
        val currentState = _uiState.value
        when (action) {
            is ProfileUiAction.OnLogOutButtonClick -> {
                if (currentState is ProfileUiState.Success) {
                    viewModelScope.launch {
                        _uiState.value = currentState.copy(isLoading = true)
                        logOutUseCase(Unit).fold(
                            onSuccess = {
                                _uiState.value = ProfileUiState.SuccessAnonymous(isLoading = false)
                            },
                            onFailure = {
                                _uiState.value = currentState.copy(isLoading = false)
                                Log.e("ProfileViewModel", "Logout failed: ${it.message}")
                            },
                        )
                    }
                }
            }
        }
    }
}
