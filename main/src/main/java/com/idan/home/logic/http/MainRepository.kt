package com.idan.home.logic.http

import com.idan.frame.model.PageInfo
import com.idan.home.logic.model.Albums
import com.idan.home.logic.model.Category
import com.idan.home.logic.model.Message

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark Main仓库
 */
class MainRepository(private val loginService: MainService) {

    suspend fun queryMessage(userId:Long,page:Int): PageInfo<Message> = loginService.queryMessages(userId, page, 5)


    suspend fun queryCategories(map: MutableMap<String,String>):MutableList<Category> = loginService.queryCategories(map)

    suspend fun queryAlbumsList(map: MutableMap<String,String>):PageInfo<Albums> = loginService.queryAlbumsList(map)


}