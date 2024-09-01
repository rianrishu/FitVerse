package com.rianrishu.fitverse.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rianrishu.fitverse.utils.Avatars
import java.io.Serializable

@Entity(tableName = "user_table")
data class UserEntity(
    val username: String = "",
    @PrimaryKey
    val email: String = "",
    val profileImage: String = "",
    val hasCustomImage: Boolean = false,
    val customImageType: Avatars = Avatars.AVATAR_1
): Serializable
