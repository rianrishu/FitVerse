package com.rianrishu.fitverse.data.model.entity

import com.rianrishu.fitverse.utils.Avatars

data class UserEntity(
    val username: String = "",
    val email: String = "",
    val profileImage: String = "",
    val hasCustomImage: Boolean = false,
    val customImageType: Avatars = Avatars.AVATAR_1
)