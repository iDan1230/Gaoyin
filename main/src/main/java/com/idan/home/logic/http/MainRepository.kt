package com.idan.home.logic.http

import com.idan.frame.model.PageInfo
import com.idan.home.logic.model.Message

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark Main仓库
 */
class MainRepository(private val loginService: MainService) {

    suspend fun queryMessage(userId:Long,page:Int): PageInfo<Message> = loginService.queryMessages(userId, page, 5)

}