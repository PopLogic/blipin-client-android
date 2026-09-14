package com.poplogic.blipin.presentation.authenticate.presentation.emailverification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poplogic.blipin.common.base.ratelimit.RateLimitStatus
import com.poplogic.blipin.domain.auth.usecase.EmailSignInUseCase
import com.poplogic.blipin.domain.auth.usecase.EmailVerificationParam
import com.poplogic.blipin.domain.auth.usecase.EmailVerificationResendRateLimitStatusUseCase
import com.poplogic.blipin.domain.auth.usecase.EmailVerificationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration

@KoinViewModel
class EmailVerificationViewModel(
    private val emailVerificationUseCase: EmailVerificationUseCase,
    private val emailSignInUseCase: EmailSignInUseCase,
    private val emailVerificationResendRateLimitStatusUseCase: EmailVerificationResendRateLimitStatusUseCase,
    private val email: String,
) : ViewModel() {
    private companion object {
        const val OTP_LENGTH = 6
    }

    private val _uiState = MutableStateFlow(EmailVerificationUiState.initialState(email))
    val uiState: StateFlow<EmailVerificationUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<EmailVerificationUiEffect>()
    val eventFlow: SharedFlow<EmailVerificationUiEffect> = _eventFlow
    private val verifyMutex = Mutex()

    init {
        viewModelScope.launch {
            emailVerificationResendRateLimitStatusUseCase(email).collect { rateLimitStatus ->
                when (rateLimitStatus) {
                    is RateLimitStatus.Available -> {
                        _uiState.value =
                            _uiState.value.copy(
                                canResendOtp = true,
                                remainingTime = Duration.ZERO,
                            )
                    }

                    is RateLimitStatus.Locked -> {
                        _uiState.value =
                            _uiState.value.copy(
                                canResendOtp = false,
                                remainingTime = rateLimitStatus.theRemainingTimeMs,
                            )
                    }
                }
            }
        }
    }

    fun onAction(action: EmailVerificationUiAction) {
        when (action) {
            is EmailVerificationUiAction.OnOtpChanged -> {
                _uiState.value = _uiState.value.copy(otp = action.otp)
                if (_uiState.value.otp.length == OTP_LENGTH) {
                    verifyOtp()
                }
            }

            is EmailVerificationUiAction.OnVerifyButtonClicked -> {
                verifyOtp()
            }

            is EmailVerificationUiAction.OnResendOtpButtonClicked -> {
                resendOtp()
            }
        }
    }

    private fun verifyOtp() {
        val currentState = _uiState.value
        if (currentState.isLoading || currentState.otp.length != OTP_LENGTH || !verifyMutex.tryLock()) {
            return
        }

        _uiState.value = currentState.copy(isLoading = true)
        viewModelScope.launch {
            try {
                emailVerificationUseCase
                    .invoke(
                        EmailVerificationParam(
                            email = currentState.email,
                            verificationCode = currentState.otp,
                        ),
                    ).fold(
                        onSuccess = {
                            _uiState.value = _uiState.value.copy(isLoading = false)
                            _eventFlow.emit(EmailVerificationUiEffect.VerificationSuccess)
                        },
                        onFailure = { e ->
                            _uiState.value = _uiState.value.copy(isLoading = false)
                            _eventFlow.emit(
                                EmailVerificationUiEffect.VerificationFailed(
                                    e.message ?: "Verification failed",
                                ),
                            )
                        },
                    )
            } finally {
                verifyMutex.unlock()
            }
        }
    }

    private fun resendOtp() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            emailSignInUseCase(email).fold(
                onSuccess = {
                },
                onFailure = { e ->
                    Log.e("EmailVerificationViewModel", "Resend OTP failed: ${e.message}")
                },
            )
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}
