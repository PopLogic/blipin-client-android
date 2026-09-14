package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.common.UseCase

interface AppleSignInUseCase : UseCase<String, Unit> {
    override suspend operator fun invoke(param: String): Result<Unit>
}
