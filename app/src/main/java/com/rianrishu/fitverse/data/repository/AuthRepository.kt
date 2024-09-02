package com.rianrishu.fitverse.data.repository

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.rianrishu.fitverse.data.api.local.datasource.PreferencesDataSource
import com.rianrishu.fitverse.data.api.remote.datasource.FirebaseAuthDataSource
import com.rianrishu.fitverse.data.model.mapper.UserMapper
import com.rianrishu.fitverse.data.model.remote.UserDTO
import com.rianrishu.fitverse.utils.ErrorType
import com.rianrishu.fitverse.utils.NetworkUtils
import com.rianrishu.fitverse.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val userRepository: UserRepository,
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

    suspend fun registerUsingGoogle(data: Intent): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (!networkUtils.checkInternetConnection())
                return@withContext Resource.Error(
                    message = "No internet",
                    errorType = ErrorType.NO_INTERNET
                )
            val account = authDataSource.getGoogleAccount(data)
            if (account is Resource.Error) {
                return@withContext Resource.Error(
                    message = account.message,
                    errorType = ErrorType.UNKNOWN
                )
            }

            val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
            val resource = authDataSource.signInUsingCredential(credential)
            val userDTO = resource.data?.let {
                getUserDTOFromFirebaseUser(it)
            } ?: UserDTO()
            return@withContext storeUserDataAfterRegister(resource, userDTO)
        }

    private fun getUserDTOFromFirebaseUser(firebaseUser: FirebaseUser) = UserDTO(
        email = firebaseUser.email.toString(),
        username = firebaseUser.displayName.toString(),
        profile_img = firebaseUser.photoUrl.toString()
    )

    private suspend fun <T> storeUserDataAfterRegister(
        resource: Resource<T>,
        userDTO: UserDTO
    ): Resource<Unit> {
        userRepository.addUser(userMapper.toDomainModel(userDTO))
        preferencesDataSource.saveUserData(userMapper.toDomainModel(userDTO))
        return Resource.Success(message = resource.message)
    }

    suspend fun loginUser(email: String, password: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (!networkUtils.checkInternetConnection())
                return@withContext Resource.Error(
                    message = "No internet",
                    errorType = ErrorType.NO_INTERNET
                )
            val resource = authDataSource.loginUser(email, password)
            return@withContext getUserDataAfterLogin(resource, email)
        }

    suspend fun loginUsingGoogle(data: Intent): Resource<Unit> =
        withContext(Dispatchers.IO) {
            if (!networkUtils.checkInternetConnection())
                return@withContext Resource.Error(
                    message = "No internet",
                    errorType = ErrorType.NO_INTERNET
                )
            val account = authDataSource.getGoogleAccount(data)
            if (account is Resource.Error)
                return@withContext Resource.Error("Failed to sign in using Google")
            val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
            val resource = authDataSource.signInUsingCredential(credential)
            return@withContext getUserDataAfterLogin(resource, resource.data!!.email!!)
        }

    private suspend fun <T> getUserDataAfterLogin(
        authResource: Resource<T>,
        email: String
    ): Resource<Unit> {
        val dbResource = userRepository.getUser(email)
        if (authResource is Resource.Error || dbResource is Resource.Error) {
            if (authResource is Resource.Success && dbResource is Resource.Error) {
                removeUser()
                return Resource.Error(message = "User not found! Please Sign Up")
            }
            return Resource.Error(message = authResource.message)
        }
        return Resource.Success(message = authResource.message)
    }

    suspend fun logoutUser() {
        authDataSource.logoutUser()
        userRepository.deleteUser()
        preferencesDataSource.removeUserData()
    }

    private suspend fun removeUser() {
        authDataSource.removeUser()
        userRepository.deleteUser()
        preferencesDataSource.removeUserData()
    }
}
