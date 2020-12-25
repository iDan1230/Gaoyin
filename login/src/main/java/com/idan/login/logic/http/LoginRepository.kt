package com.idan.login.logic.http

import com.idan.login.logic.pojo.User

class LoginRepository(private val loginService: LoginService) {

    suspend fun login(account: String, password: String): User = loginService.login(account, password)
}