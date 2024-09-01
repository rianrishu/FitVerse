package com.rianrishu.fitverse.data.api.remote.harperdb

import com.rianrishu.fitverse.data.model.remote.SQLModel
import com.rianrishu.fitverse.data.model.remote.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/")
    suspend fun saveUserInDb(
        @Body body: SQLModel
    ): Response<Any>

    @POST("/")
    suspend fun getUserInfo(
        @Body sqlModel: SQLModel
    ): Response<List<UserDTO>>
}
