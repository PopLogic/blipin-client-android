package com.poplogic.blipin.data.user

import com.poplogic.blipin.api.user.models.UpdateUserProfileRequest
import com.poplogic.blipin.api.user.models.User
import com.poplogic.blipin.api.user.models.User.Gender
import com.poplogic.blipin.domain.user.model.UserEntity

class UserMapper {
    fun mapUserDtoToUserEntity(userDto: User): UserEntity =
        UserEntity(
            displayName = userDto.displayName,
            gender = mapUserGenderToGender(userDto.gender),
            birthdate = userDto.birthdate,
            profilePictureUrl = userDto.avatarUrl,
        )

    fun mapUserGenderToGender(gender: Gender?): UserEntity.Gender? =
        when (gender) {
            Gender.male -> UserEntity.Gender.MALE
            Gender.female -> UserEntity.Gender.FEMALE
            Gender.other -> UserEntity.Gender.OTHER
            else -> null
        }

    fun mapGenderToUserGender(gender: UserEntity.Gender?): UpdateUserProfileRequest.Gender? =
        when (gender) {
            UserEntity.Gender.MALE -> UpdateUserProfileRequest.Gender.male
            UserEntity.Gender.FEMALE -> UpdateUserProfileRequest.Gender.female
            UserEntity.Gender.OTHER -> UpdateUserProfileRequest.Gender.other
            else -> null
        }
}
