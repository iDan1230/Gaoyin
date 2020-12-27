package com.idan.login.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.idan.frame.base.BaseViewModel
import com.idan.login.logic.http.LoginRepository
import com.idan.login.logic.pojo.User

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    val account = ObservableField<String>("18181820180@")

    val password = ObservableField<String>("11111111")

    val userLiveData = MutableLiveData<User>()

    fun onLogin() {
        ARouter.getInstance().build("/main/main").navigation()
//        launch {
//            userLiveData.value = loginRepository.login(account.get()!!, password.get()!!)
//        }
    }
}