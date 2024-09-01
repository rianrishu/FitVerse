package com.rianrishu.fitverse.data.api.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.rianrishu.fitverse.data.model.entity.UserEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferencesDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) :
    PreferencesDataSource {

    companion object {
        val USER_SAVE_KEY = stringPreferencesKey("user")
        val SERVICE_KEY = booleanPreferencesKey("Service")
    }

    override fun getUserData(): UserEntity? = null

    override fun getUserDataFlow() = dataStore.data.map {
        val user = it[USER_SAVE_KEY]
        Gson().fromJson(user, UserEntity::class.java)
    }

    override suspend fun saveUserData(userEntity: UserEntity) {
        val serializedUser = Gson().toJson(userEntity)
        dataStore.edit {
            it[USER_SAVE_KEY] = serializedUser
        }
    }

    override suspend fun removeUserData() {
        dataStore.edit {
            it.remove(USER_SAVE_KEY)
        }
    }

    override fun isServiceRunningByFlow() = dataStore.data.map {
        val isRunning = it[SERVICE_KEY]
        isRunning != null
    }

    override suspend fun setServiceRunning(running: Boolean) {
        dataStore.edit {
            it[SERVICE_KEY] = running
        }
    }

    override fun isServiceRunning(): Boolean {
        return true
    }

    override fun isOnBoardingComplete(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setOnBoardingComplete() {
        TODO("Not yet implemented")
    }
}
