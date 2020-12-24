package com.idan.login.ui.login

import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.idan.frame.ID
import com.idan.frame.TITLE
import com.idan.frame.base.BaseViewModel
import com.idan.login.logic.http.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    val account = ObservableField<String>("11111111")

    val password = ObservableField<String>("22222222")

    fun onLogin() {
        ARouter.getInstance().build("/main/main")
            .withLong(ID,10010L)
            .withString(TITLE,"TEST_TITLE")
            .navigation()
    }
}