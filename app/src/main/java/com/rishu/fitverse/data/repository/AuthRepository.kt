package com.rishu.fitverse.data.repository

import com.rishu.fitverse.data.api.local.datasource.PreferencesDataSource
import com.rishu.fitverse.data.api.remote.datasource.FirebaseAuthDataSource
import com.rishu.fitverse.data.api.remote.datasource.HarperDbAuthDataSource
import com.rishu.fitverse.data.model.mapper.UserMapper
import com.rishu.fitverse.utils.NetworkUtils
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val harperDbAuthDataSource: HarperDbAuthDataSource,
    @Named("sharedPref") private val preferencesDataSource: PreferencesDataSource,
    private val userMapper: UserMapper,
    private val networkUtils: NetworkUtils
) {
    suspend fun logoutUser() {
        authDataSource.logoutUser()
        preferencesDataSource.removeUserData()
    }

    private suspend fun removeUser() {
        authDataSource.removeUser()
        preferencesDataSource.removeUserData()
    }

}
