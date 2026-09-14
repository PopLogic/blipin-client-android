package com.poplogic.blipin.data.user

import com.poplogic.blipin.api.user.UserRemoteDataSource
import com.poplogic.blipin.api.user.models.UpdateUserProfileRequest
import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.domain.user.repository.UserRepository
import com.poplogic.blipin.domain.user.usecase.UpdateUserProfileParam

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userMapper: UserMapper,
) : UserRepository {
    override suspend fun getUser(): UserEntity {
        val getUserProfileResponse = userRemoteDataSource.getUser()
        return userMapper.mapUserDtoToUserEntity(getUserProfileResponse.user)
    }

    override suspend fun updateUserProfile(updateUserProfileParam: UpdateUserProfileParam): UserEntity {
        val updateUserProfileResponse =
            userRemoteDataSource.updateUser(
                UpdateUserProfileRequest(
                    displayName = updateUserProfileParam.displayName,
                    gender = userMapper.mapGenderToUserGender(updateUserProfileParam.gender),
                    birthdate = updateUserProfileParam.birthdate?.toString(),
                ),
            )
        return userMapper.mapUserDtoToUserEntity(updateUserProfileResponse.user)
    }
}
