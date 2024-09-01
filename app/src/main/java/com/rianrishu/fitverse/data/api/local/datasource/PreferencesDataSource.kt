package com.rianrishu.fitverse.data.api.local.datasource

import com.rianrishu.fitverse.data.model.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {

    fun getUserData(): UserEntity?

    fun getUserDataFlow(): Flow<UserEntity>

    suspend fun saveUserData(userEntity: UserEntity)

    suspend fun removeUserData()

    fun isServiceRunning(): Boolean

    fun isServiceRunningByFlow(): Flow<Boolean>

    suspend fun setServiceRunning(running: Boolean)

    fun isOnBoardingComplete(): Boolean

    suspend fun setOnBoardingComplete()

}
