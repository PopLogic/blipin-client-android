package com.poplogic.blipin.domain.user.usecase

import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.domain.user.repository.UserRepository

class UpdateUserProfileUseCaseImpl(
    private val userRepository: UserRepository,
) : UpdateUserProfileUseCase {
    override suspend operator fun invoke(param: UpdateUserProfileParam): Result<UserEntity> =
        try {
            val userEntity = userRepository.updateUserProfile(param)
            Result.success(userEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
