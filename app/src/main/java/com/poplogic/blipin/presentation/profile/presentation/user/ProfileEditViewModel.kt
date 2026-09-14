package com.poplogic.blipin.presentation.profile.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.common.base.util.hardcoded
import com.poplogic.blipin.domain.user.model.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

enum class ProfileDataItem(
    val title: String,
    val uiAction: ProfileEditScreenUiAction,
) {
    DisplayName("用戶名稱".hardcoded(), ProfileEditScreenUiAction.OnDisplayNameEditButtonClick),
    Birthdate("西元生日".hardcoded(), ProfileEditScreenUiAction.OnBirthdateEditButtonClick),
    Gender("性別".hardcoded(), ProfileEditScreenUiAction.OnGenderEditButtonClick),
}

@KoinViewModel
class ProfileEditViewModel(
    val userEntity: UserEntity?,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            if (userEntity == null) {
                ProfileEditScreenUiState.Error
            } else {
                ProfileEditScreenUiState.Success(
                    userEntity,
                )
            },
        )

    val uiState: MutableStateFlow<ProfileEditScreenUiState> = _uiState

    private val _uiEffect = MutableStateFlow<ProfileEditScreenUiEffect?>(null)
    val uiEffect: MutableStateFlow<ProfileEditScreenUiEffect?> = _uiEffect

    fun onAction(action: ProfileEditScreenUiAction) {
        when (_uiState.value) {
            is ProfileEditScreenUiState.Success -> {
                val currentUserEntity =
                    (_uiState.value as ProfileEditScreenUiState.Success).userEntity
                when (action) {
                    is ProfileEditScreenUiAction.OnDisplayNameEditButtonClick -> {
                        viewModelScope.launch {
                            _uiEffect.emit(
                                ProfileEditScreenUiEffect.ShowDisplayNameEditBottomSheet(
                                    currentDisplayName = currentUserEntity.displayName ?: "",
                                ),
                            )
                        }
                    }

                    is ProfileEditScreenUiAction.OnBirthdateEditButtonClick -> {
                        viewModelScope.launch {
                            _uiEffect.emit(
                                ProfileEditScreenUiEffect.ShowBirthdateEditBottomSheet(
                                    currentBirthdate = currentUserEntity.birthdate,
                                ),
                            )
                        }
                    }

                    is ProfileEditScreenUiAction.OnGenderEditButtonClick -> {
                        viewModelScope.launch {
                            _uiEffect.emit(
                                ProfileEditScreenUiEffect.ShowGenderEditBottomSheet(
                                    currentGender = currentUserEntity.gender,
                                ),
                            )
                        }
                    }

                    is ProfileEditScreenUiAction.OnProfilePictureEditButtonClick -> {
                    }
                }
            }

            else -> {
                return
            }
        }
    }
}
