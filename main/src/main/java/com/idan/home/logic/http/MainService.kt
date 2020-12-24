package com.idan.home.logic.http

import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MainService {

    @POST("/SSSS/register")
    fun register(@QueryMap map:MutableMap<String,String>):String

    @POST("/SSSSS/login")
    fun login(@Query("account") account:String,@Query("password") password:String):String

    @POST("/SSSS/queryUserInfo")
    fun queryUser()


}