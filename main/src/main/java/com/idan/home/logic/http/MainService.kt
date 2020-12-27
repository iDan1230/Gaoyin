package com.idan.home.logic.http

import com.idan.frame.model.PageInfo
import com.idan.home.logic.model.Albums
import com.idan.home.logic.model.Category
import com.idan.home.logic.model.Message
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

    /**
     * 类型列表
     */
    @GET("/categories/list")
    suspend fun queryCategories(@QueryMap map: MutableMap<String, String>): MutableList<Category>


    @GET("/v2/albums/list")
    suspend fun queryAlbumsList(@QueryMap params:MutableMap<String,String>): PageInfo<Albums>
}