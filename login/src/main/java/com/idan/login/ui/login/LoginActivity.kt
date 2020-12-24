package com.idan.login.ui.login

import com.idan.frame.base.BaseActivity
import com.idan.login.R
import com.idan.login.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Creator yangzhidan
 * @Date    2020/12/21/0021
 * @Remark  登录页
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val vm:LoginViewModel by viewModel()

    override fun layoutRes(): Int = R.layout.activity_login

    override fun onBindData(mDB: ActivityLoginBinding) {
        mDB.lifecycleOwner = this
        mDB.user = vm
    }

}