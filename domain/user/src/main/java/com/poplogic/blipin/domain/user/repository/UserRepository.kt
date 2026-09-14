package com.poplogic.blipin.domain.user.repository

import com.poplogic.blipin.domain.user.model.UserEntity
import com.poplogic.blipin.domain.user.usecase.UpdateUserProfileParam

interface UserRepository {
    suspend fun getUser(): UserEntity

    suspend fun updateUserProfile(updateUserProfileParam: UpdateUserProfileParam): UserEntity
}
