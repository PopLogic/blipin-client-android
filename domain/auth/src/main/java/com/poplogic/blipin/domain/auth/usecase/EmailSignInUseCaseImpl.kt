package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.auth.AuthManager
import com.poplogic.blipin.domain.auth.IsNewUser

class EmailSignInUseCaseImpl(
    private val authManager: AuthManager,
) : EmailSignInUseCase {
    override suspend fun invoke(param: String): Result<IsNewUser> {
        try {
            val isNewUser = authManager.signInWithEmail(param)
            return Result.success(isNewUser)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
