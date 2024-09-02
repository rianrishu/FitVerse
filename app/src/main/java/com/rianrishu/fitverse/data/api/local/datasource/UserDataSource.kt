package com.rianrishu.fitverse.data.api.local.datasource

import com.rianrishu.fitverse.data.api.local.room.UserDAO
import com.rianrishu.fitverse.data.model.entity.UserEntity
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userDAO: UserDAO
) {
    suspend fun getUser(email: String) = userDAO.getUser(email)

    suspend fun insertUser(userEntity: UserEntity) = userDAO.insertUser(userEntity)

    suspend fun updateUser(userEntity: UserEntity) = userDAO.updateUser(userEntity)

    suspend fun deleteUser(userEntity: UserEntity) = userDAO.deleteUser(userEntity)

    suspend fun deleteAllUser() = userDAO.deleteAllUser()
}
