package com.poplogic.blipin.domain.user.model

import android.os.Parcelable
import com.poplogic.blipin.common.base.util.hardcoded
import kotlinx.datetime.LocalDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val displayName: String?,
    val gender: Gender?,
    val birthdate: LocalDate?,
    val profilePictureUrl: String?,
) : Parcelable {
    // TODO: replace the hardcoded values with resource strings id
    enum class Gender(
        val uiName: String,
    ) {
        MALE("男".hardcoded()),
        FEMALE("女".hardcoded()),
        OTHER("其他".hardcoded()),
    }
}
