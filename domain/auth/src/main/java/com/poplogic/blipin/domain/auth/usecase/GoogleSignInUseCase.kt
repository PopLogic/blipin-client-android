package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.auth.IsNewUser
import com.poplogic.blipin.domain.common.UseCase

interface GoogleSignInUseCase : UseCase<String, IsNewUser> {
    override suspend operator fun invoke(param: String): Result<IsNewUser>
}
