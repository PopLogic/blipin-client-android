package com.poplogic.blipin.domain.user.usecase

import com.poplogic.blipin.domain.common.UseCase
import com.poplogic.blipin.domain.user.model.UserEntity
import java.time.LocalDate

data class UpdateUserProfileParam(
    val displayName: String?,
    val gender: UserEntity.Gender?,
    val birthdate: LocalDate?,
)

interface UpdateUserProfileUseCase : UseCase<UpdateUserProfileParam, UserEntity> {
    override suspend operator fun invoke(param: UpdateUserProfileParam): Result<UserEntity>
}
