package com.poplogic.blipin.presentation.authenticate.presentation.updateprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.domain.user.usecase.UpdateUserProfileParam
import com.poplogic.blipin.domain.user.usecase.UpdateUserProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UpdateUserProfileViewModel(
    nickName: String,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateUserProfileUiState(nickName))
    val uiState: StateFlow<UpdateUserProfileUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<UpdateUserProfileUiEffect>()
    val eventFlow: SharedFlow<UpdateUserProfileUiEffect> = _eventFlow

    fun onAction(action: UpdateUserProfileUiAction) {
        when (action) {
            is UpdateUserProfileUiAction.OnNicknameChanged -> {
                _uiState.value = _uiState.value.copy(nickname = action.nickname)
            }

            is UpdateUserProfileUiAction.OnGenderChanged -> {
                _uiState.value = _uiState.value.copy(gender = action.gender)
            }

            is UpdateUserProfileUiAction.OnBirthdayChanged -> {
                _uiState.value = _uiState.value.copy(birthday = action.birthday)
            }

            is UpdateUserProfileUiAction.OnSubmitButtonClicked -> {
                submitProfile()
            }
        }
    }

    private fun submitProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                updateUserProfileUseCase(
                    UpdateUserProfileParam(
                        displayName = _uiState.value.nickname,
                        gender = _uiState.value.gender,
                        birthdate =
                            _uiState.value.birthday
                                ?.toLocalDateTime(TimeZone.currentSystemDefault())
                                ?.date
                                ?.toJavaLocalDate(),
                    ),
                )

                _uiState.value = _uiState.value.copy(isLoading = false)
                _eventFlow.emit(UpdateUserProfileUiEffect.ProfileUpdateSuccess)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _eventFlow.emit(
                    UpdateUserProfileUiEffect.ProfileUpdateFailed(
                        e.message ?: "Failed to update profile",
                    ),
                )
            }
        }
    }
}
