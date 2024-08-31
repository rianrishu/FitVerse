package com.rishu.fitverse.data.repository

import com.rishu.fitverse.data.api.local.datasource.PreferencesDataSource
import com.rishu.fitverse.data.api.remote.datasource.FirebaseAuthDataSource
import com.rishu.fitverse.data.api.remote.datasource.HarperDbAuthDataSource
import com.rishu.fitverse.data.model.mapper.UserMapper
import com.rishu.fitverse.data.model.remote.UserDTO
import com.rishu.fitverse.utils.ErrorType
import com.rishu.fitverse.utils.NetworkUtils
import com.rishu.fitverse.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val harperDbAuthDataSource: HarperDbAuthDataSource,
    @Named("sharedPref") private val preferencesDataSource: PreferencesDataSource,
    private val userMapper: UserMapper,
    private val networkUtils: NetworkUtils
) {
    suspend fun registerUser(email: String, username: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (!networkUtils.checkInternetConnection())
                return@withContext Resource.Error(
                    message = "No internet",
                    errorType = ErrorType.NO_INTERNET
                )
            val resource =
                authDataSource.registerUser(email, username = username, password = password)
            val userDTO = UserDTO(email = email, username = username)
            return@withContext storeUserDataAfterRegister(resource, userDTO)
        }

    private suspend fun <T>storeUserDataAfterRegister(
        resource: Resource<T>,
        userDTO: UserDTO
    ): Resource<Unit> {
        val harperResource = harperDbAuthDataSource.storeUserIntoDb(userDTO)
        if (harperResource is Resource.Error || resource is Resource.Error) {
            if (harperResource is Resource.Error && resource is Resource.Success) {
                removeUser()
            }
            return Resource.Error(message = resource.message, errorType = ErrorType.UNKNOWN)
        }
        preferencesDataSource.saveUserData(userMapper.toDomainModel(userDTO))
        return Resource.Success(message = "User registered successfully")
    }

    suspend fun logoutUser() {
        authDataSource.logoutUser()
        preferencesDataSource.removeUserData()
    }

    private suspend fun removeUser() {
        authDataSource.removeUser()
        preferencesDataSource.removeUserData()
    }

}
