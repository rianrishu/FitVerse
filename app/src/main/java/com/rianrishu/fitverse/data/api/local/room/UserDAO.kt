package com.rianrishu.fitverse.data.api.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rianrishu.fitverse.data.model.entity.UserEntity

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()
}
