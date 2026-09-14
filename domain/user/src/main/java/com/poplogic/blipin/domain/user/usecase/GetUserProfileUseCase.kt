package com.poplogic.blipin.domain.user.usecase

import com.poplogic.blipin.domain.common.UseCase
import com.poplogic.blipin.domain.user.model.UserEntity

interface GetUserProfileUseCase : UseCase<Unit, UserEntity> {
    override suspend operator fun invoke(param: Unit): Result<UserEntity>
}
