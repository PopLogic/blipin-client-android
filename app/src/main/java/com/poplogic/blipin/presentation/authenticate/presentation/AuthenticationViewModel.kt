package com.poplogic.blipin.presentation.authenticate.presentation

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.poplogic.blipin.domain.auth.usecase.AppleSignInUseCase
import com.poplogic.blipin.domain.auth.usecase.EmailSignInUseCase
import com.poplogic.blipin.domain.auth.usecase.GoogleSignInUseCase
import com.poplogic.blipin.domain.user.usecase.GetUserProfileUseCase
import com.poplogic.blipin.domain.user.usecase.UpdateUserProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AuthenticationViewModel(
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val appleSignInUseCase: AppleSignInUseCase,
    private val emailSignInUseCase: EmailSignInUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(AuthenticationUiState())
    val uiState: StateFlow<AuthenticationUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<AuthenticationUiEffect>(extraBufferCapacity = 1)
    val eventFlow: SharedFlow<AuthenticationUiEffect> = _eventFlow

    fun onAction(action: AuthenticationUiAction) {
        when (action) {
            is AuthenticationUiAction.OnCredentialStarted -> {
                viewModelScope.launch {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = true,
                        )
                }
            }

            is AuthenticationUiAction.OnCredentialFailed -> {
                viewModelScope.launch {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                        )
                }
            }

            is AuthenticationUiAction.OnCredentialReturned -> {
                val credential = action.credential

                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    viewModelScope.launch {
                        googleSignInUseCase(googleCredential.idToken).fold(
                            onSuccess = { isNewUser ->
                                getUserProfileUseCase(Unit).fold(
                                    onSuccess = { userEntity ->
                                        _uiState.value =
                                            _uiState.value.copy(
                                                isLoading = false,
                                                credentialDisplayName = userEntity.displayName,
                                            )
                                        if (isNewUser) {
                                            _eventFlow.emit(
                                                AuthenticationUiEffect.ShowCredentialSignUpBottomSheet(
                                                    displayName = userEntity.displayName,
                                                ),
                                            )
                                        } else {
                                            _eventFlow.emit(
                                                AuthenticationUiEffect.NavigateToHomeScreen(
                                                    isPasswordBeenUpdated = false,
                                                ),
                                            )
                                        }
                                    },
                                    onFailure = {
                                        _uiState.value =
                                            _uiState.value.copy(
                                                isLoading = false,
                                            )
                                        return@fold
                                    },
                                )
                            },
                            onFailure = {
                                Log.e(
                                    "AuthenticationViewModel",
                                    "User identity sign-in failed: ${it.message}",
                                )
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                    )
                                return@launch
                            },
                        )
                    }
                }
            }

            is AuthenticationUiAction.OnEmailTextFieldChanged -> {
                _uiState.value =
                    _uiState.value.copy(
                        email = action.email,
                    )
            }

            is AuthenticationUiAction.OnEmailSignInButtonClicked -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(isLoading = true)

                    emailSignInUseCase(action.email).fold(
                        onSuccess = { isNewUser ->
                            _uiState.value = _uiState.value.copy(isLoading = false)

                            if (isNewUser) {
                                _eventFlow.emit(
                                    AuthenticationUiEffect.ShowEmailSignUpBottomSheet(
                                        action.email,
                                    ),
                                )
                            } else {
                                _eventFlow.emit(
                                    AuthenticationUiEffect.ShowEmailSignInBottomSheet(
                                        action.email,
                                    ),
                                )
                            }
                        },
                        onFailure = {
                            _uiState.value = _uiState.value.copy(isLoading = false)
                            _eventFlow.emit(
                                AuthenticationUiEffect.ShowEmailCheckError(
                                    "Failed to check email registration",
                                ),
                            )
                        },
                    )
                }
            }

            is AuthenticationUiAction.OnEmailTextFieldCleared -> {
                _uiState.value = _uiState.value.copy(email = "")
            }
        }
    }
}
