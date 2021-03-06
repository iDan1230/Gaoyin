package com.idan.login.logic.http

import com.idan.login.logic.pojo.User
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("/mobile/sessionManage/loginFourteam.action")
    suspend fun login(
        @Query("userName") account: String, @Query("password") passwork: String, @Query(
            "sysCode"
        ) sysCode: String = "51"
    ): User

}