package com.poplogic.blipin.domain.user.usecase

import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.domain.user.repository.UserRepository

class GetUserProfileUseCaseImpl(
    private val userRepository: UserRepository,
) : GetUserProfileUseCase {
    override suspend operator fun invoke(param: Unit): Result<UserEntity> =
        try {
            val userProfile = userRepository.getUser()
            Result.success(userProfile)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
