package com.idan.home.logic.http

import com.idan.frame.model.PageInfo
import com.idan.home.logic.model.AlbumsVO
import com.idan.home.logic.model.CategoryVO
import com.idan.home.logic.model.Message
import com.idan.home.logic.model.TagVO

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark Main仓库
 */
class MainRepository(private val loginService: MainService) {

    suspend fun queryMessage(userId: Long, page: Int): PageInfo<Message> =
        loginService.queryMessages(userId, page, 5)

    suspend fun queryCategories(map: MutableMap<String, String>): MutableList<CategoryVO> =
        loginService.queryCategories(map)

    suspend fun queryTags(map: MutableMap<String, String>): MutableList<TagVO> =
        loginService.queryTags(map)

    suspend fun queryAlbumsList(map: MutableMap<String, String>): PageInfo<AlbumsVO> =
        loginService.queryAlbumsList(map)

}