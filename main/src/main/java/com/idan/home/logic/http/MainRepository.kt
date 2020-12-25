package com.idan.home.logic.http

import com.idan.home.logic.model.Message

/**
 * @Author yangzhidan
 * @Date   2020/12/25
 * @Remark Main仓库
 */
class MainRepository(private val loginService: MainService) {

    suspend fun queryMessage(): ArrayList<Message> = loginService.queryMessages(199916, 1, 20).rows

}