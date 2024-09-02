package com.rianrishu.fitverse.data.repository

import com.rianrishu.fitverse.data.api.local.datasource.UserDataSource
import com.rianrishu.fitverse.data.model.entity.UserEntity
import com.rianrishu.fitverse.utils.NetworkUtils
import com.rianrishu.fitverse.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) {
    suspend fun addUser(userEntity: UserEntity): Resource<Unit> = withContext(Dispatchers.IO) {
        userDataSource.deleteAllUser()
        userDataSource.insertUser(userEntity)
        Resource.Success(message = "User added successfully")
    }

    suspend fun getUser(email: String): Resource<UserEntity> = withContext(Dispatchers.IO) {
        val user = userDataSource.getUser(email)
        if (user != null) {
            Resource.Success(data = user)
        } else {
            Resource.Error()
        }
    }

    suspend fun deleteUser(): Resource<Unit> = withContext(Dispatchers.IO) {
        userDataSource.deleteAllUser()
        Resource.Success(message = "User deleted successfully")
    }
}
