package com.idan.home.logic.http

import com.idan.home.logic.model.Message
import com.idan.home.logic.model.PageInfo
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MainService {
    @GET("mobile/msgSendInfo/msgSendInfoPageList.action")
    suspend fun queryMessages(
        @Query("userId") userId: Long,
        @Query("page") page: Int,
        @Query("rows") rows: Int
    ): PageInfo<Message>


    @POST("/SSSS/register")
    fun register(@QueryMap map: MutableMap<String, String>): String

    @POST("/SSSSS/login")
    fun login(@Query("account") account: String, @Query("password") password: String): String

    @POST("/SSSS/queryUserInfo")
    fun queryUser()


}