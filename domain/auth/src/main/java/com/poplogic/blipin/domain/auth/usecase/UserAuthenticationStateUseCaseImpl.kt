package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.auth.AuthManager
import com.poplogic.blipin.domain.auth.model.UserAuthenticationState
import kotlinx.coroutines.flow.Flow

class UserAuthenticationStateUseCaseImpl(
    private val authManager: AuthManager,
) : UserAuthenticationStateUseCase {
    override fun invoke(param: Unit): Flow<UserAuthenticationState> = authManager.userAuthenticationStateFlow
}
