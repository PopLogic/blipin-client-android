package com.poplogic.blipin.domain.auth.usecase

import com.poplogic.blipin.domain.common.UseCase

data class EmailVerificationParam(
    val email: String,
    val verificationCode: String,
)

interface EmailVerificationUseCase : UseCase<EmailVerificationParam, Unit> {
    override suspend operator fun invoke(param: EmailVerificationParam): Result<Unit>
}
